package com.ruoyi.jgc.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.jgc.domain.Fllcjl;
import com.ruoyi.jgc.domain.Flsqd;
import com.ruoyi.jgc.domain.RadiotherapyDto;
import com.ruoyi.jgc.service.IFllcjlService;
import com.ruoyi.jgc.service.IFlsqdService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
    @Autowired
    private IFllcjlService fllcjlService;

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
    public AjaxResult add(@RequestBody List<RadiotherapyDto> radiotherapys)
    {

        if (CollectionUtils.isEmpty(radiotherapys)) {
            return AjaxResult.success();
        }

        for (RadiotherapyDto radiotherapy : radiotherapys) {
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
                        r.setCreateBy(getUsername());
                        r.setCreateTime(new Date());
                        list.add(r);
                        stepDays++;
                    }

                    logger.info("批量新增{}个治疗对象", list.size());
                    radiotherapyService.batchInsert(list);
                }
            } else {
                logger.info("单个治疗对象新增");
                radiotherapyService.insertRadiotherapy(radiotherapy);
            }
        }
        Flsqd flsqd = flsqdService.selectFlsqdById(radiotherapys.get(0).getFldId());
        if ("wks".equals(flsqd.getTreatStatus())) {
            Flsqd update = new Flsqd();
            update.setId(flsqd.getId());
            update.setTreatStatus("jxz");
            flsqdService.updateFlsqd(update);
            logger.info("放疗单[{}] 已安排了治疗时间，疗程状态由“未开始”改为“进行中” ", flsqd.getId());
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
     * 修改放射治疗时间安排
     */
//    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:edit')")
    @Log(title = "放射治疗", businessType = BusinessType.UPDATE)
    @PutMapping("/updateSchTime")
    public AjaxResult updateSchTime(@RequestBody Map<String, Object> map)
    {
        logger.info("修改时间 {}", JSON.toJSONString(map));
        Date d = new Date();
        d.setTime((long) map.get("schTime"));
        String updateAll = (String) map.get("updateAll");
        Integer id = (Integer) map.get("id");

        List<Radiotherapy> updateList = new ArrayList<>();
        if ("1".equals(updateAll)) {
            logger.info("将时间应用到所有放射治疗");
            Radiotherapy query = new Radiotherapy();
            query.setFldId((String) map.get("fldId"));
            query.setCureStatus("0");//未治疗
            List<Radiotherapy> list = radiotherapyService.selectRadiotherapyList(query);
            logger.info("查询到{}条未治疗记录  {}", list.size(), map.get("schTime").getClass().getName());
            Integer HH = Integer.valueOf(DateUtils.parseDateToStr("HH", d));

            for (Radiotherapy r : list) {
                if (r.getId().equals(id)) {
                    r.setSchTime(d);
                } else {
                    r.setSchTime(DateUtils.setHours(r.getSchTime(), HH));
                }
                updateList.add(r);

            }
        } else {
            Radiotherapy r = new Radiotherapy();
            r.setId(Long.valueOf(id));
            r.setSchTime(d);
            updateList.add(r);
        }
        for (Radiotherapy r : updateList) {
            radiotherapyService.updateRadiotherapy(r);
        }
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('fl:radiotherapy:edit')")
    @Log(title = "放射治疗", businessType = BusinessType.UPDATE)
    @PutMapping("/endCure")
    @Transactional
    public AjaxResult endCure(@RequestBody Map<String, Object> map)
    {
        logger.info("结束治疗操作 入口参数 {}", JSON.toJSONString(map));
        String fldId = (String) map.get("fldId");
        Long id = Long.valueOf(map.get("id").toString());
        //查询fldId下的所有状态为为治疗的 放射诊疗
        Radiotherapy query = new Radiotherapy();
        query.setFldId(fldId);
        List<Radiotherapy> list = radiotherapyService.selectRadiotherapyList(query);
        List<Radiotherapy> noEndList = list.stream().filter(r -> !r.getCureStatus().equals("5")).collect(Collectors.toList());//未结束的治疗


        boolean lastFlag = false;
        if(noEndList.size() == 1) {
            if (!noEndList.get(0).getId().equals(id)) {
                logger.error("系统异常，数据库最后一次未治疗id[{}]和传入id[{}]不匹配",
                        JSON.toJSONString(noEndList.get(0)), JSON.toJSONString(map));
                throw new RuntimeException("数据库最后一次未治疗id和传入id不匹配");
            }
            //是最后一次治疗，需要将放疗单疗程状态改为‘结束’
            lastFlag = true;
        } else if (noEndList.size() < 1) {
            logger.error("系统异常，数据库记录中不存在状态为未治疗的记录（可能全部治疗已经结束）",
                    JSON.toJSONString(noEndList.get(0)), JSON.toJSONString(map));
            throw new RuntimeException("数据库记录中不存在状态为未治疗的记录（可能全部治疗已经结束）");
        }

        Radiotherapy r = new Radiotherapy();
        r.setId(id);
        r.setCureStatus("5");//结束
        r.setCureEndTime(new Date());
        r.setUpdateBy(getUsername());
        r.setCureFlag("Y");
        r.setRemark((String)map.get("remark"));
        r.setCureOperator((String)map.get("operatorNames"));
        radiotherapyService.updateRadiotherapy(r);

        List<Integer> opertaor = (List<Integer>) map.get("opertaorIds");
        if (!CollectionUtils.isEmpty(opertaor)) {
            for (Integer i : opertaor) {
                Fllcjl jl = new Fllcjl();
                jl.setLcjdmc("fzsl");
                jl.setFlid(fldId);
                jl.setCzsj(r.getCureEndTime());
                jl.setCzr(Long.valueOf(i));
                jl.setCreateBy(getUsername());
                jl.setCreateTime(new Date());
                jl.setLcjdxh(-1);//-1标识空
                fllcjlService.insertFllcjl(jl);
            }
        }

        Flsqd flsqd = new Flsqd();
        flsqd.setId(fldId);
        flsqd.setCuredCount(list.stream().filter(rad->rad.getCureStatus().equals("5")).count() + 1);
        if (lastFlag) {
            flsqd.setTreatStatus("yjs");//结束整个疗程
        }
        flsqdService.updateFlsqd(flsqd);

        return AjaxResult.success();
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

    public static void main(String[] args) throws ParseException {
        String str = "2024-05-20T01:00:00Z";
        Date date = DateUtils.parseDate(str, "yyyy-MM-dd'T'HH:mm:ssZ");
        String s = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", date);
        System.out.println(s);

        DateFormat.getInstance();

    }
}
