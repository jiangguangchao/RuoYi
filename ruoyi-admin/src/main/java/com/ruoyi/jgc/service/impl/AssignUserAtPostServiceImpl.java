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
 * @description:
 * @author:
 * @create: 2024-03-23 15:42
 */

@Service
public class AssignUserAtPostServiceImpl extends AbstractAssignService<SysUserPostVo, Long>{

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
    public boolean checkStatus(SysUserPostVo t) {
        return "0".equals(t.getStatus()) && "1".equals(t.getAssignWork());
    }
}
