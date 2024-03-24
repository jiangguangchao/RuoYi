package com.ruoyi.jgc.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.jgc.domain.Flsqd;
import com.ruoyi.jgc.service.IFlsqdService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 放疗申请单Controller
 * 
 * @author jgc
 * @date 2023-10-28
 */
@RestController
@RequestMapping("/fl/flsqd")
public class FlsqdController extends BaseController
{
    @Autowired
    private IFlsqdService flsqdService;

    /**
     * 查询放疗申请单列表
     */
    @PreAuthorize("@ss.hasPermi('fl:flsqd:list')")
    @GetMapping("/list")
    public TableDataInfo list(Flsqd flsqd)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        if (!SecurityUtils.isAdmin(userId)) {
            flsqd.setDqczry(userId);
        }
        List<Flsqd> list = flsqdService.selectFlsqdList(flsqd);
        return getDataTable(list);
    }

    /**
     * 导出放疗申请单列表
     */
    @PreAuthorize("@ss.hasPermi('fl:flsqd:export')")
    @Log(title = "放疗申请单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Flsqd flsqd)
    {
        List<Flsqd> list = flsqdService.selectFlsqdList(flsqd);
        ExcelUtil<Flsqd> util = new ExcelUtil<Flsqd>(Flsqd.class);
        util.exportExcel(response, list, "放疗申请单数据");
    }

    /**
     * 获取放疗申请单详细信息
     */
    @PreAuthorize("@ss.hasPermi('fl:flsqd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(flsqdService.selectFlsqdById(id));
    }

    @GetMapping(value = "/newId")
    public AjaxResult getNewId()
    {
        return AjaxResult.success("success", flsqdService.getNewId());
    }

    /**
     * 新增放疗申请单
     */
    @PreAuthorize("@ss.hasPermi('fl:flsqd:add')")
    @Log(title = "放疗申请单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Flsqd flsqd)
    {

        // flsqd.setId(DateUtils.dateTimeNow("yyyyMMddHHmmss"));
        flsqd.setCreateBy(getUsername());
        logger.info("新增放疗申请单 {}", JSON.toJSONString(flsqd));
        
        return toAjax(flsqdService.insertFlsqd(flsqd));
    }

    @PreAuthorize("@ss.hasPermi('fl:flsqd:start')")
    @Log(title = "放疗申请单", businessType = BusinessType.UPDATE)
    @PostMapping("/start")
    public AjaxResult start(@RequestBody Flsqd flsqd)
    {

        logger.info("开启放疗申请单 {}", JSON.toJSONString(flsqd));
        return toAjax(flsqdService.startLC(flsqd));
    }

    @PreAuthorize("@ss.hasPermi('fl:flsqd:sign')")
    @Log(title = "放疗申请单", businessType = BusinessType.UPDATE)
    @PostMapping("/sign")
    public AjaxResult sign(@RequestBody Flsqd flsqd)
    {

        logger.info("放疗申请单签名 {}", JSON.toJSONString(flsqd));
        return toAjax(flsqdService.sign(flsqd, null));
    }

    /**
     * 修改放疗申请单
     */
    @PreAuthorize("@ss.hasPermi('fl:flsqd:edit')")
    @Log(title = "放疗申请单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Flsqd flsqd)
    {
        return toAjax(flsqdService.updateFlsqd(flsqd));
    }

    /**
     * 删除放疗申请单
     */
    @PreAuthorize("@ss.hasPermi('fl:flsqd:remove')")
    @Log(title = "放疗申请单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(flsqdService.deleteFlsqdByIds(ids));
    }
}
