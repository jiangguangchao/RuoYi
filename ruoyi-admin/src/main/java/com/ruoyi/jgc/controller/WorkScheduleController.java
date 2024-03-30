package com.ruoyi.jgc.controller;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.CollectionUtils;
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
import com.ruoyi.jgc.domain.WorkSchedule;
import com.ruoyi.jgc.service.IWorkScheduleService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 排班Controller
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
@RestController
@RequestMapping("/fl/schedule")
public class WorkScheduleController extends BaseController
{
    @Autowired
    private IWorkScheduleService workScheduleService;

    /**
     * 查询排班列表
     */
    @PreAuthorize("@ss.hasPermi('fl:schedule:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkSchedule workSchedule)
    {
        startPage();
        List<WorkSchedule> list = workScheduleService.selectWorkScheduleList(workSchedule);
        return getDataTable(list);
    }


    /**
     * 查询排班列表
     */
    // @PreAuthorize("@ss.hasPermi('fl:schedule:list')")
    @GetMapping("/tableList")
    public AjaxResult tableList(WorkSchedule workSchedule)
    {
        // startPage();
        List<WorkSchedule> list = workScheduleService.selectWorkScheduleList(workSchedule);
        if (CollectionUtils.isEmpty(list)) {
            return AjaxResult.success();
        }
        
        Map<Long,List<WorkSchedule>> userMap = list.stream().collect(Collectors.groupingBy(WorkSchedule::getUserId));
        userMap.forEach((k, v) -> {
            v.sort(Comparator.comparingLong(w-> w.getSchDate().getTime()));
        });
        return AjaxResult.success(userMap);
    }

    public String getDayOfWeek(int index) {
        switch (index) {
            case 0:
                return "周日";
            
            case 1:
                return "周一";
            
            case 2:
                return "周二";
            
            case 3:
                return "周三";
            
            case 4:
                return "周四";
            
            case 5:
                return "周五";
            
            case 6:
                return "周六";

            default:
                return "无效索引";
        }
    }


    /**
     * 导出排班列表
     */
    @PreAuthorize("@ss.hasPermi('fl:schedule:export')")
    @Log(title = "排班", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkSchedule workSchedule)
    {
        List<WorkSchedule> list = workScheduleService.selectWorkScheduleList(workSchedule);
        ExcelUtil<WorkSchedule> util = new ExcelUtil<WorkSchedule>(WorkSchedule.class);
        util.exportExcel(response, list, "排班数据");
    }

    /**
     * 获取排班详细信息
     */
    @PreAuthorize("@ss.hasPermi('fl:schedule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(workScheduleService.selectWorkScheduleById(id));
    }

    /**
     * 保存排班信息
     */
    @PreAuthorize("@ss.hasPermi('fl:schedule:save')")
    @Log(title = "排班", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    @Transactional
    public AjaxResult save(@RequestBody List<WorkSchedule> list)
    {
        if (CollectionUtils.isEmpty(list)) {
            return AjaxResult.success();
        }
        list.forEach(w -> {
            if (w.getId() != null) {
                edit(w);
            } else {
                add(w);
            }
        });
        return AjaxResult.success();
    }

    /**
     * 新增排班
     */
    @PreAuthorize("@ss.hasPermi('fl:schedule:add')")
    @Log(title = "排班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkSchedule workSchedule)
    {
        return toAjax(workScheduleService.insertWorkSchedule(workSchedule));
    }

    /**
     * 修改排班
     */
    @PreAuthorize("@ss.hasPermi('fl:schedule:edit')")
    @Log(title = "排班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkSchedule workSchedule)
    {
        return toAjax(workScheduleService.updateWorkSchedule(workSchedule));
    }

    /**
     * 删除排班
     */
    @PreAuthorize("@ss.hasPermi('fl:schedule:remove')")
    @Log(title = "排班", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(workScheduleService.deleteWorkScheduleByIds(ids));
    }
}
