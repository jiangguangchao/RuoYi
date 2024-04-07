package com.ruoyi.jgc.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.SysUserPostVo;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysUserService;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ruoyi
 * @description: 在指定的岗位上维护一条员工队列（每个岗位都有自己独立的队列），
 * 用于排列当前岗位上多个员工接受任务顺序。比如放疗定位岗有 U1,U2,U3三个用户，
 * 在队列中排列是U1,U2,U3。 U1排第一意味着下一个任务是U1先接受，当U1接受任务后，
 * 队列就变成了U2,U3,U1
 * @author:
 * @create: 2024-03-23 15:42
 */

@Service
public class AssignUserAtPostServiceImpl extends AbstractAssignService<SysUserPostVo, Long>{

    //指定那些岗位需要维护一个员工队列
    public static final String[] postArr = {"dw", "bqgh", "bqhz","bqtj","jhsj","jhhz","fwyz"};

    @Autowired
    private ISysPostService postService;
    @Autowired
    private ISysUserService sysUserService;

    @Override
    public String getType() {
        return "POST";
    }

    @Override
    public List<SysUserPostVo> getBeanListFromDB(String postCode) {
        return postService.selectUserByPost(postCode);
    }

    @Override
    public SysUserPostVo getBeanById(Long Id) {
        SysUser sysUser = sysUserService.selectUserById(Id);
        SysUserPostVo vo = new SysUserPostVo();
        BeanUtils.copyProperties(sysUser, vo);
        return vo;
    }

    public boolean checkSlotId(String slotId) {
        return ArrayUtils.contains(postArr, slotId);
    }

    @Override
    public boolean checkBeanStatus(SysUserPostVo t) {
        return "0".equals(t.getStatus()) && "1".equals(t.getAssignWork());
    }
}
