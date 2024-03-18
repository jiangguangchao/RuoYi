package com.ruoyi.jgc.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.SysUserPostVo;

import java.util.List;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-15 17:18
 */
public interface IAssignWorkService {


    /**
     * 根据岗位编号查询当前岗位任务分配用户顺序
     *
     * @param postCode 岗位编号
     * @return 用户列表
     */
    public List<SysUserPostVo> getAssignUsersByPost(String postCode);

    public List<SysUserPostVo> refreshAssignUsersByPost(String postCode);

    public boolean addUserAtPost(String postCode, Long userId);

    public boolean removeUserAtPost(String postCode, Long userId);

    public void userPostChange(Long userId, String postCode, String prePostCode);


}
