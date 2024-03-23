package com.ruoyi.jgc.controller;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.jgc.domain.Machine;
import com.ruoyi.jgc.service.IMachineService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 放疗机器Controller
 * 
 * @author ruoyi
 * @date 2024-03-23
 */
@RestController
@RequestMapping("/fl/machine")
public class MachineController extends BaseController
{
    @Autowired
    private IMachineService machineService;

    /**
     * 查询放疗机器列表
     */
    @PreAuthorize("@ss.hasPermi('fl:machine:list')")
    @GetMapping("/list")
    public TableDataInfo list(Machine machine)
    {
        startPage();
        List<Machine> list = machineService.selectMachineList(machine);
        return getDataTable(list);
    }

    /**
     * 导出放疗机器列表
     */
    @PreAuthorize("@ss.hasPermi('fl:machine:export')")
    @Log(title = "放疗机器", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Machine machine)
    {
        List<Machine> list = machineService.selectMachineList(machine);
        ExcelUtil<Machine> util = new ExcelUtil<Machine>(Machine.class);
        util.exportExcel(response, list, "放疗机器数据");
    }

    /**
     * 获取放疗机器详细信息
     */
    @PreAuthorize("@ss.hasPermi('fl:machine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(machineService.selectMachineById(id));
    }

    /**
     * 新增放疗机器
     */
    @PreAuthorize("@ss.hasPermi('fl:machine:add')")
    @Log(title = "放疗机器", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Machine machine)
    {
        return toAjax(machineService.insertMachine(machine));
    }

    /**
     * 修改放疗机器
     */
    @PreAuthorize("@ss.hasPermi('fl:machine:edit')")
    @Log(title = "放疗机器", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Machine machine)
    {
        return toAjax(machineService.updateMachine(machine));
    }

    /**
     * 删除放疗机器
     */
    @PreAuthorize("@ss.hasPermi('fl:machine:remove')")
    @Log(title = "放疗机器", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(machineService.deleteMachineByIds(ids));
    }
}
