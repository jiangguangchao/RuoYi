package com.ruoyi.jgc.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.jgc.domain.FllcjlDto;
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
import com.ruoyi.jgc.domain.Fllcjl;
import com.ruoyi.jgc.service.IFllcjlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 放疗流程记录Controller
 * 
 * @author ruoyi
 * @date 2023-10-31
 */
@RestController
@RequestMapping("/fl/fllcjl")
public class FllcjlController extends BaseController
{
    @Autowired
    private IFllcjlService fllcjlService;

    /**
     * 查询放疗流程记录列表
     */
    @PreAuthorize("@ss.hasPermi('fl:fllcjl:list')")
    @GetMapping("/list")
    public TableDataInfo list(Fllcjl fllcjl)
    {
        startPage();
        List<FllcjlDto> list = fllcjlService.selectFllcjlList(fllcjl);
        return getDataTable(list);
    }

    /**
     * 导出放疗流程记录列表
     */
    @PreAuthorize("@ss.hasPermi('fl:fllcjl:export')")
    @Log(title = "放疗流程记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Fllcjl fllcjl)
    {
//        List<FllcjlDto> list = fllcjlService.selectFllcjlList(fllcjl);
//        ExcelUtil<FllcjlDto> util = new ExcelUtil<FllcjlDto>(FllcjlDto.class);
//        util.exportExcel(response, list, "放疗流程记录数据");
    }

    /**
     * 获取放疗流程记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('fl:fllcjl:query')")
    @GetMapping(value = "/{flid}")
    public AjaxResult getInfo(@PathVariable("flid") String flid)
    {
        return AjaxResult.success(fllcjlService.selectFllcjlByFlid(flid));
    }

    /**
     * 新增放疗流程记录
     */
    @PreAuthorize("@ss.hasPermi('fl:fllcjl:add')")
    @Log(title = "放疗流程记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Fllcjl fllcjl)
    {
        return toAjax(fllcjlService.insertFllcjl(fllcjl));
    }

    /**
     * 修改放疗流程记录
     */
    @PreAuthorize("@ss.hasPermi('fl:fllcjl:edit')")
    @Log(title = "放疗流程记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Fllcjl fllcjl)
    {
        return toAjax(fllcjlService.updateFllcjl(fllcjl));
    }

    /**
     * 删除放疗流程记录
     */
    @PreAuthorize("@ss.hasPermi('fl:fllcjl:remove')")
    @Log(title = "放疗流程记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{flids}")
    public AjaxResult remove(@PathVariable String[] flids)
    {
        return toAjax(fllcjlService.deleteFllcjlByFlids(flids));
    }
}
