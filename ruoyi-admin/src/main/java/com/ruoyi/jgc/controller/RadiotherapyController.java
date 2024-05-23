package com.ruoyi.jgc.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.jgc.domain.Flsqd;
import com.ruoyi.jgc.domain.RadiotherapyDto;
import com.ruoyi.jgc.service.IFlsqdService;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.jgc.domain.Radiotherapy;
import com.ruoyi.jgc.service.IRadiotherapyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 放射治疗Controller
 * 
 * @author jgc
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/fl/radiotherapy")
public class RadiotherapyController extends BaseController
{
    @Autowired
    private IRadiotherapyService radiotherapyService;
    @Autowired
    private IFlsqdService flsqdService;

    /**
     * 查询放射治疗列表
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:list')")
    @GetMapping("/list")
    public TableDataInfo list(Radiotherapy radiotherapy)
    {
        startPage();
        List<Radiotherapy> list = radiotherapyService.selectRadiotherapyList(radiotherapy);

        list.sort((o1, o2) -> {
            if (o1.getMachineId().equals(o2.getMachineId())) {
                long time1 = o1.getSchTime() == null ? -1l : o1.getSchTime().getTime();
                long time2 = o2.getSchTime() == null ? -1l : o2.getSchTime().getTime();
                return (int) (time1 - time2);
            } else {
                return o1.getMachineId().compareTo(o2.getMachineId());
            }
        });

        return getDataTable(list);
    }

    /**
     * 查询未来一天的放射治疗列表
     */
    // @PreAuthorize("@ss.hasPermi('fl:radiotherapy:list')")
    @GetMapping("/list/future")
    public AjaxResult listFuture(Radiotherapy radiotherapy)
    {
        List<Radiotherapy> list = radiotherapyService.selectRadiotherapyList(radiotherapy);

        //对list排序， 先按照machineId排序，相同的machineId,并且schTime不为空，则按照schTime升序排列
        
        list.sort((o1, o2) -> {
            if (o1.getMachineId().equals(o2.getMachineId())) {
                if (o1.getSchTime() != null && o2.getSchTime() != null) {
                    return o1.getSchTime().compareTo(o2.getSchTime());
                }
                else {
                    return 0;
                }
            } else {
                return o1.getMachineId().compareTo(o2.getMachineId());
            }
        });

        logger.info("排序后 {}", JSON.toJSONString(list));

        return AjaxResult.success(list);
    }

    /**
     * 导出放射治疗列表
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:export')")
    @Log(title = "放射治疗", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Radiotherapy radiotherapy)
    {
        List<Radiotherapy> list = radiotherapyService.selectRadiotherapyList(radiotherapy);
        ExcelUtil<Radiotherapy> util = new ExcelUtil<Radiotherapy>(Radiotherapy.class);
        util.exportExcel(response, list, "放射治疗数据");
    }

    /**
     * 获取放射治疗详细信息
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(radiotherapyService.selectRadiotherapyById(id));
    }

    /**
     * 查询放疗单
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:add')")
    @GetMapping(value = "/fld")
    public AjaxResult getFld(Flsqd flsqd)
    {
        return AjaxResult.success(flsqdService.selectFlsqdList(flsqd));
    }

    /**
     * 新增放射治疗
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:add')")
    @Log(title = "放射治疗", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RadiotherapyDto radiotherapy)
    {
        if ("wks".equals(radiotherapy.getTreatStatus())) {
            //如果整个疗程的状态是“未开始”,则这里新增多个Radiotherapy对象，每个对象代表疗程中的一次治疗
            logger.info("当前疗程状态未开始，开始批量新增整个疗程治疗");
            Integer cureCount = radiotherapy.getCureCount();
            if (cureCount == null || cureCount < 1) {
                logger.info("批量新增整个疗程治疗时，总治疗次数不合规（不存在或者小于1）  {}", JSON.toJSONString(radiotherapy));
            } else {
                int stepDays = 0;
                List<Radiotherapy> list = new ArrayList<>();
                for (int i = 0; i < cureCount; i++) {
                    Radiotherapy r = new Radiotherapy();
                    BeanUtils.copyProperties(radiotherapy, r);
                    Date date = DateUtils.addDays(radiotherapy.getSchTime(), stepDays);
                    if (i > 0 && i < cureCount-1) {
                        //如果中间的某一天在周六或者周日，调整到下周一
                        int i1 = DateUtils.toCalendar(date).get(Calendar.DAY_OF_WEEK);
                        if (i1 == 7) {
                            //周六
                            stepDays = stepDays + 2;
                        }
                        date = DateUtils.addDays(radiotherapy.getSchTime(), stepDays);
                    }
                    r.setSchTime(date);
                    r.setCureFlag("N");
                    r.setCureStatus("0");
                    r.setCreateBy(getUsername());
                    r.setCreateTime(new Date());
                    list.add(r);

                }

                logger.info("批量新增{}个治疗对象", list.size());
                radiotherapyService.batchInsert(list);
            }
        } else {
            radiotherapyService.insertRadiotherapy(radiotherapy);
        }
        return AjaxResult.success();
    }

    /**
     * 修改放射治疗
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:edit')")
    @Log(title = "放射治疗", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Radiotherapy radiotherapy)
    {
        return toAjax(radiotherapyService.updateRadiotherapy(radiotherapy));
    }

    /**
     * 移除放射治疗时间安排
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:edit')")
    @Log(title = "放射治疗", businessType = BusinessType.UPDATE)
    @PutMapping("/removeSchTime")
    public AjaxResult removeSchTime(@RequestBody Radiotherapy radiotherapy)
    {
        return toAjax(radiotherapyService.removeSchTime(radiotherapy.getId()));
    }

    /**
     * 删除放射治疗
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:remove')")
    @Log(title = "放射治疗", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(radiotherapyService.deleteRadiotherapyByIds(ids));
    }
}
