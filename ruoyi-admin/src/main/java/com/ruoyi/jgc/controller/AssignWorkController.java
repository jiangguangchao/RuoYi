package com.ruoyi.jgc.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUserPostVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.jgc.domain.Fllcjl;
import com.ruoyi.jgc.service.IAssignWorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-18 11:27
 */

@RestController
@RequestMapping("/fl/assignWork")
public class AssignWorkController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AssignWorkController.class);

    @Autowired
    private IAssignWorkService assignWorkService;

//    @PreAuthorize("@ss.hasPermi('fl:assignWork:list')")
    @GetMapping("/list")
    public List<SysUserPostVo> list(String postCode)
    {
        log.info("查询岗位任务分配顺序入口参数 {}", postCode);
        List<SysUserPostVo> assignUsersByPost = assignWorkService.getAssignUsersByPost(postCode);

        List<String> userNames = assignUsersByPost.stream().map(u -> u.getUserName()).collect(Collectors.toList());
        log.info("查询岗位任务分配顺序 结果 {}", userNames);
        return assignUsersByPost;
    }

    @GetMapping("/refresh")
    public List<SysUserPostVo> refresh(String postCode)
    {
        List<SysUserPostVo> assignUsersByPost = assignWorkService.refreshAssignUsersByPost(postCode);
        return assignUsersByPost;
    }


}
