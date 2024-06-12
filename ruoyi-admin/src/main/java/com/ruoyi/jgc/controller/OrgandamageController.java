package com.ruoyi.jgc.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
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
import com.ruoyi.jgc.domain.Organdamage;
import com.ruoyi.jgc.service.IOrgandamageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 器官危害Controller
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
@RestController
@RequestMapping("/fl/organdamage")
public class OrgandamageController extends BaseController
{
    @Autowired
    private IOrgandamageService organdamageService;

    /**
     * 查询器官危害列表
     */
//    @PreAuthorize("@ss.hasPermi('fl:organdamage:list')")
    @GetMapping("/list")
    public TableDataInfo list(Organdamage organdamage)
    {
        startPage();
        List<Organdamage> list = organdamageService.selectOrgandamageList(organdamage);
        return getDataTable(list);
    }

    /**
     * 导出器官危害列表
     */
//    @PreAuthorize("@ss.hasPermi('fl:organdamage:export')")
    @Log(title = "器官危害", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Organdamage organdamage)
    {
        List<Organdamage> list = organdamageService.selectOrgandamageList(organdamage);
        ExcelUtil<Organdamage> util = new ExcelUtil<Organdamage>(Organdamage.class);
        util.exportExcel(response, list, "器官危害数据");
    }

    /**
     * 获取器官危害详细信息
     */
//    @PreAuthorize("@ss.hasPermi('fl:organdamage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(organdamageService.selectOrgandamageById(id));
    }

    /**
     * 新增器官危害
     */
//    @PreAuthorize("@ss.hasPermi('fl:organdamage:add')")
    @Log(title = "器官危害", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Organdamage organdamage)
    {
        logger.info("新增器官危害 入库参数记录 {}", JSON.toJSONString(organdamage));
        return toAjax(organdamageService.insertOrgandamage(organdamage));
    }

    /**
     * 修改器官危害
     */
//    @PreAuthorize("@ss.hasPermi('fl:organdamage:edit')")
    @Log(title = "器官危害", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Organdamage organdamage)
    {
        return toAjax(organdamageService.updateOrgandamage(organdamage));
    }

    /**
     * 删除器官危害
     */
//    @PreAuthorize("@ss.hasPermi('fl:organdamage:remove')")
    @Log(title = "器官危害", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(organdamageService.deleteOrgandamageByIds(ids));
    }
}
