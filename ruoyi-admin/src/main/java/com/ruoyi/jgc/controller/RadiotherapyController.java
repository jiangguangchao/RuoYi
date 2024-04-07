package com.ruoyi.jgc.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.utils.DateUtils;
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

    /**
     * 查询放射治疗列表
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:list')")
    @GetMapping("/list")
    public TableDataInfo list(Radiotherapy radiotherapy)
    {
        startPage();
        List<Radiotherapy> list = radiotherapyService.selectRadiotherapyList(radiotherapy);
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
     * 新增放射治疗
     */
    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:add')")
    @Log(title = "放射治疗", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Radiotherapy radiotherapy)
    {
        return toAjax(radiotherapyService.insertRadiotherapy(radiotherapy));
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
