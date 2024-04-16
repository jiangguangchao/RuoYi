package com.ruoyi.jgc.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUserPostVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.jgc.domain.Fllcjl;
import com.ruoyi.jgc.service.IAssignWorkService;
import com.ruoyi.jgc.service.impl.AssignUserAtPostServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    // @Autowired
    // private IAssignWorkService assignWorkService;

    @Autowired
    private AssignUserAtPostServiceImpl assignUserAtPostService;

//    @PreAuthorize("@ss.hasPermi('fl:assignWork:list')")
    @GetMapping("/list")
    public List<SysUserPostVo> list(String postCode)
    {
        List<SysUserPostVo> assignUsersByPost = assignUserAtPostService.getAssignList(postCode);
        return assignUsersByPost;
    }

    @GetMapping("/refresh")
    public List<SysUserPostVo> refresh(String postCode)
    {
        List<SysUserPostVo> assignUsersByPost = assignUserAtPostService.refreshAssignList(postCode);
        log.info("刷新后：{}", JSON.toJSONString(assignUsersByPost));
        List<SysUserPostVo> assignList = assignUserAtPostService.getAssignList(postCode);
        log.info("刷新后通过get查询：{}", JSON.toJSONString(assignList));
        return assignUsersByPost;
    }

}
