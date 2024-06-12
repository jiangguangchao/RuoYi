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
import com.ruoyi.jgc.domain.Targetregion;
import com.ruoyi.jgc.service.ITargetregionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 靶区Controller
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
@RestController
@RequestMapping("/fl/targetregion")
public class TargetregionController extends BaseController
{
    @Autowired
    private ITargetregionService targetregionService;

    /**
     * 查询靶区列表
     */
//    @PreAuthorize("@ss.hasPermi('fl:targetregion:list')")
    @GetMapping("/list")
    public TableDataInfo list(Targetregion targetregion)
    {
        startPage();
        List<Targetregion> list = targetregionService.selectTargetregionList(targetregion);
        return getDataTable(list);
    }

    /**
     * 导出靶区列表
     */
//    @PreAuthorize("@ss.hasPermi('fl:targetregion:export')")
    @Log(title = "靶区", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Targetregion targetregion)
    {
        List<Targetregion> list = targetregionService.selectTargetregionList(targetregion);
        ExcelUtil<Targetregion> util = new ExcelUtil<Targetregion>(Targetregion.class);
        util.exportExcel(response, list, "靶区数据");
    }

    /**
     * 获取靶区详细信息
     */
//    @PreAuthorize("@ss.hasPermi('fl:targetregion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(targetregionService.selectTargetregionById(id));
    }

    /**
     * 新增靶区
     */
//    @PreAuthorize("@ss.hasPermi('fl:targetregion:add')")
    @Log(title = "靶区", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Targetregion targetregion)
    {
        return toAjax(targetregionService.insertTargetregion(targetregion));
    }

    /**
     * 修改靶区
     */
//    @PreAuthorize("@ss.hasPermi('fl:targetregion:edit')")
    @Log(title = "靶区", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Targetregion targetregion)
    {
        return toAjax(targetregionService.updateTargetregion(targetregion));
    }

    /**
     * 删除靶区
     */
//    @PreAuthorize("@ss.hasPermi('fl:targetregion:remove')")
    @Log(title = "靶区", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(targetregionService.deleteTargetregionByIds(ids));
    }
}
