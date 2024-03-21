package com.ruoyi.jgc.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.SysUserPostVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.jgc.domain.Fllcjl;
import com.ruoyi.jgc.domain.FllcjlDto;
import com.ruoyi.jgc.domain.WorkloadDto;
import com.ruoyi.jgc.domain.WorkloadQuery;
import com.ruoyi.jgc.service.IFllcjlService;
import com.ruoyi.jgc.service.impl.AssignWorkServiceImpl;
import com.ruoyi.jgc.service.impl.FlsqdServiceImpl;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: ruoyi
 * @description:工作量查询
 * @author:
 * @create: 2024-03-21 10:57
 */


@RestController
@RequestMapping("/fl/workload")
public class WorkLoadController extends BaseController {

    @Autowired
    private IFllcjlService fllcjlService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysPostService postService;


    @PreAuthorize("@ss.hasPermi('fl:workload:list')")
    @GetMapping("/list")
    public TableDataInfo list(Fllcjl query)
    {
        startPage();
        List<FllcjlDto> list = fllcjlService.selectWorkload(query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('fl:workload:stat')")
    @GetMapping("/stat")
    public List<WorkloadDto> workloadStat(Fllcjl query)
    {
        List<WorkloadDto> list = fllcjlService.workloadStat(query);
        List<SysUser> sysUsers = userService.selectAllUsers();

        //过滤掉已经删除的用户
        Map<Long, SysUser> map = sysUsers.stream().filter(u-> "0".equals(u.getDelFlag())).collect(Collectors.toMap(SysUser::getUserId, Function.identity()));
        List<WorkloadDto> resultList = list.stream()
                .filter(w -> map.containsKey(w.getUserId()))
                .map(w -> {
                    w.setUserName(map.get(w.getUserId()).getUserName());
                    return w;
                })
                .collect(Collectors.toList());


        return resultList;
    }

    @GetMapping("/fls")
    public List getFls() {
        List<SysUserPostVo> list = new ArrayList<>();
        for (String s : AssignWorkServiceImpl.postArr) {
            List<SysUserPostVo> sysUserPostVos = postService.selectUserByPost(s);
            list.addAll(sysUserPostVos);
        }
        return list;
    }


}
