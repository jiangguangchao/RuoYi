package com.ruoyi.jgc.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.SysUserPostVo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.jgc.service.IAssignWorkService;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-15 17:27
 */

@Service
public class AssignWorkServiceImpl implements IAssignWorkService {

    private static final Logger log = LoggerFactory.getLogger(AssignWorkServiceImpl.class);

    public static final String FLSQD_ASSIGN_WORK_POST_ ="FLSQD_ASSIGN_WORK_POST_";

    public static final String[] postArr = {"dw", "bqgh", "bqhz","bqtj","jhsj","jhhz","fwyz"};

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysPostService postService;

    @Override
    public List<SysUserPostVo> getAssignUsersByPost(String postCode) {
        if (!ArrayUtils.contains(postArr, postCode)) {
            return null;
        }
//        String redisKey = FLSQD_ASSIGN_WORK_POST_ + postCode;
//        List<SysUserPostVo> cacheList = redisCache.getCacheList(redisKey);
//        if (CollectionUtils.isEmpty(cacheList)) {
//            cacheList = refreshAssignUsersByPost(postCode);
//        }
        return refreshAssignUsersByPost(postCode);
    }

    @Override
    public List<SysUserPostVo> refreshAssignUsersByPost(String postCode) {
        if (!ArrayUtils.contains(postArr, postCode)) {
            return null;
        }
        log.info("开始执行岗位[{}]任务分配顺序刷新处理", postCode);
        List<SysUserPostVo> sysUserPostVos = postService.selectUserByPost(postCode);
        if (sysUserPostVos == null) {
            sysUserPostVos = new ArrayList<>();
        }

        List<SysUserPostVo> usersAtPost = sysUserPostVos
                .stream()
                .filter(u -> "0".equals(u.getStatus()) && "1".equals(u.getAssignWork()))
                .collect(Collectors.toList());
        List<Long> userIdsAtPost = usersAtPost.stream().map(u->u.getUserId()).collect(Collectors.toList());

        String redisKey = FLSQD_ASSIGN_WORK_POST_ + postCode;
        List<SysUserPostVo> cacheList = redisCache.getCacheList(redisKey);
        if (cacheList == null) {
            cacheList = new ArrayList<>();
        }

        Set<Long> userIdSet = new HashSet<>();
        List<SysUserPostVo> collect = cacheList.stream()
                .filter(u -> userIdSet.add(u.getUserId()))
                .filter(u -> userIdsAtPost.contains(u.getUserId()))
                .collect(Collectors.toList());

        for (SysUserPostVo vo : usersAtPost) {
            if (collect.stream().noneMatch(u->u.getUserId().equals(vo.getUserId()))) {
                collect.add(vo);
            }
        }
        if (CollectionUtils.isEmpty(collect)) {
            redisCache.deleteObject(redisKey);
        } else {
            redisCache.setCacheList(redisKey, collect);
        }
        return collect;
    }

    @Override
    public boolean addUserAtPost(String postCode, Long userId) {
        if (!ArrayUtils.contains(postArr, postCode)) {
            return false;
        }

        SysUser sysUser = sysUserService.selectUserById(userId);
        String userName = sysUser.getUserName();
        log.info("开始在岗位[{}]任务顺序中加入用户[{}]", postCode, userName);
        if ("1".equals(sysUser.getStatus()) || "0".equals(sysUser.getAssignWork())) {
            log.error("当前用户[{}]状态已停用或停止接受任务分配，无法加入岗位[{}]分配任务序列中", userName, postCode);
            throw new ServiceException("当前用户状态已停用或停止接受任务分配，无法加入当前岗位分配任务序列中");
        }
        String redisKey = FLSQD_ASSIGN_WORK_POST_ + postCode;
        List<SysUserPostVo> cacheList = redisCache.getCacheList(redisKey);
        if (cacheList == null) {
            cacheList = new ArrayList<>();
        }

        if (cacheList.stream().anyMatch(u->u.getUserId().equals(userId))) {
            log.info("用户[{}]已经在岗位[{}]分配任务序列中，无需再次加入", sysUser, postCode);
            return true;
        }

        SysUserPostVo vo = new SysUserPostVo();
        BeanUtils.copyProperties(sysUser, vo);
        cacheList.add(vo);
        List<String> userNames = cacheList.stream().map(u -> u.getUserName()).collect(Collectors.toList());
        log.info("用户[{}]成功加入岗位[{}],当前用户序列[{}]",userName, postCode, userNames);
        redisCache.setCacheList(redisKey, cacheList);
        return true;
    }

    @Override
    public boolean removeUserAtPost(String postCode, Long userId) {
        if (!ArrayUtils.contains(postArr, postCode)) {
            return false;
        }
        SysUser sysUser = sysUserService.selectUserById(userId);
        String userName = sysUser.getUserName();
        log.info("开始从岗位[{}]任务顺序中移除用户[{}]", postCode, userName);
        String redisKey = FLSQD_ASSIGN_WORK_POST_ + postCode;
        List<SysUserPostVo> cacheList = redisCache.getCacheList(redisKey);
        if (cacheList == null) {
            cacheList = new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(cacheList)) {
            log.info("当前岗位[{}]分配任务序列为空,说明当前用户[{}]已经不在序列中", postCode, userName);
            return true;
        }
        List<SysUserPostVo> collect = cacheList.stream().filter(u -> !u.getUserId().equals(userId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            redisCache.deleteObject(redisKey);
        } else {
            redisCache.setCacheList(redisKey, collect);
        }
        log.info("用户[{}]已经从当前岗位[{}]分配任务序列中移除", userName, postCode);
        return true;
    }

    @Override
    public void userPostChange(Long userId, String postCode, String prePostCode) {
        if (StringUtils.isEmpty(postCode) && StringUtils.isEmpty(prePostCode)) {
            return;
        }
        SysUser sysUser = sysUserService.selectUserById(userId);
        String userName = sysUser.getUserName();
        log.info("用户[{}]岗位变化[{} ==> {}]，开始执行岗位任务分配顺序处理", userName, prePostCode, postCode);
        if ("1".equals(sysUser.getStatus()) || "0".equals(sysUser.getAssignWork())) {
            log.error("当前用户[{}]岗位发生变化，但状态已停用或停止接受任务分配，不做岗位任务分配序列处理", userName);
            return;
//            throw new ServiceException("当前用户状态已停用或停止接受任务分配，无法加入当前岗位分配任务序列中");
        }

        String[] posts = FlsqdServiceImpl.lcArr;
        if (ArrayUtils.contains(posts, prePostCode)) {
            log.info("当前用户[{}]岗位发生变化，且原岗位[{}]属于放疗单相关岗位角色，需从原岗位任务分配序列中移除",
                    userName, prePostCode);
            removeUserAtPost(prePostCode, userId);
        }
        if (ArrayUtils.contains(posts, postCode)) {
            log.info("当前用户[{}]岗位发生变化，且现岗位[{}]属于放疗单相关岗位角色，需加入现岗位任务分配序列中",
                    userName, postCode);
            addUserAtPost(postCode, userId);
        }

    }


}
