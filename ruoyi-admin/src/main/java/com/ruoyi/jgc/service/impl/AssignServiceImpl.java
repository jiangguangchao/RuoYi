package com.ruoyi.jgc.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.jgc.service.IAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-23 10:53
 */

@Service
public class AssignServiceImpl<T> implements IAssignService<T> {

    public static final String FLSQD_ASSIGN_ ="FLSQD_ASSIGN_";

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<T> getAssignList(String scopeId) {

        String redisKey = FLSQD_ASSIGN_ + scopeId;
        List<T> list = redisCache.getCacheList(redisKey);
        if (list == null) {
            list = refreshAssignList(scopeId);
        }
        return list;
    }

    @Override
    public List<T> refreshAssignList(String scopeId) {

        return null;
    }

    @Override
    public boolean addBean(String scopeId, T t) {
        String redisKey = FLSQD_ASSIGN_ + scopeId;
        List<T> list = redisCache.getCacheList(redisKey);
        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.contains(t)){
            return true;
        }
        list.add(t);
        redisCache.setCacheList(redisKey, list);
        return true;
    }

    @Override
    public boolean removeBean(String scopeId, T t) {
        String redisKey = FLSQD_ASSIGN_ + scopeId;
        List<T> list = redisCache.getCacheList(redisKey);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.remove(t);
        if (CollectionUtils.isEmpty(list)) {
            redisCache.deleteObject(redisKey);
        } else {
            redisCache.setCacheList(redisKey, list);
        }
        return true;
    }

    @Override
    public boolean moveToEnd(String scopeId, T t) {
        String redisKey = FLSQD_ASSIGN_ + scopeId;
        List<T> list = redisCache.getCacheList(redisKey);
        if (list == null) {
            list = new ArrayList<>();
        }
        if (!list.contains(t)) {
            return false;
        }

        list.remove(t);
        list.add(t);
        redisCache.setCacheList(redisKey, list);
        return true;
    }
}
