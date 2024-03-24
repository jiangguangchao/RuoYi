package com.ruoyi.jgc.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.jgc.service.IAssignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: ruoyi
 * @description: 用来调配操作人员或者机器接受分配任务的顺序。以岗位为例，某个岗位上，
 * 有新加入的员工，还有从该岗位移除的员工，在当前岗位上需要维护一个队列，如果有任务来到当前
 * 岗位，就会按照队列顺序指派人员，排在最前面的人员先被指派，然后该工作人员会自动移动到队列末尾。
 * 同理，当任务来到某台机器上，该机器也有这样一个队列。我们可以用一个type字段区分是岗位还是机器，
 * 然后再用一个slotId来标识岗位编号或者机器编号,bean用来表示队列中的个体
 * @author:
 * @create: 2024-03-23 10:53
 */

@Service
public abstract class AbstractAssignService<T, U> implements IAssignService<T, U> {

    public static final String FLSQD_ASSIGN_ ="FLSQD_ASSIGN_";

    private static final Logger log = LoggerFactory.getLogger(AbstractAssignService.class);


    @Autowired
    private RedisCache redisCache;

    @Override
    public List<T> getAssignList(String slotId) {
        if(!checkSlotId(slotId)) {
            return null;
        }
        String redisKey = FLSQD_ASSIGN_ + getType() + "_" + slotId;
        List<T> list = redisCache.getCacheList(redisKey);
        if (CollectionUtils.isEmpty(list)) {
            log.info("任务队列[{}-{}] 查询到redis中队列为null，开始执行刷新操作", getType(), slotId);
            list = refreshAssignList(slotId);
        }
        log.info("任务队列[{}-{}] 查询到redis中队列： {}", getType(), slotId, JSON.toJSONString(list));
        return list;
    }

    @Override
    public List<T> refreshAssignList(String slotId) {
        if(!checkSlotId(slotId)) {
            return null;
        }
        String redisKey = FLSQD_ASSIGN_ + getType() + "_" + slotId;
        List<T> listFromDB = getBeanListFromDB(slotId);
        if (CollectionUtils.isEmpty(listFromDB)) {
            redisCache.deleteObject(redisKey);
            return null;
        }

        //checkedListFromDB是刚从数据库查询出来，并且排除了状态不合适的，所以这个队列中的要全部存入redis。
        //但是要注意checkedListFromDB的元素顺序仅仅是数据库查询的排列顺序，并不一定和redis中已有队列顺序一致
        //所以顺序要参照redis中队列顺序
        List<T> checkedListFromDB = listFromDB.stream().filter(t -> checkStatus(t)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(checkedListFromDB)) {
            redisCache.deleteObject(redisKey);
            return null;
        }

        List<T> resultList = new ArrayList<>();
        List<T> listFromRedis = redisCache.getCacheList(redisKey);
        if (CollectionUtils.isEmpty(listFromRedis)) {
            resultList = checkedListFromDB;
        } else {
            //如果redis队列中有某个元素，而checkedListFromDB中没有，那么这个元素需要从redis队列移除
            List<T> collect = listFromRedis.stream().filter(t -> checkedListFromDB.contains(t)).collect(Collectors.toList());

            //接下来，如果checkedListFromDB中有，而上一步得到的collect没有，那么说明这个元素需要加入到collect末尾
            for (T t : checkedListFromDB) {
                if (!collect.contains(t)) {
                    collect.add(t);
                }
            }
            resultList = collect;
        }

        redisCache.setCacheList(redisKey, resultList);
        return resultList;
    }

    @Override
    public boolean addBeanById(String slotId, U id) {
        return addBean(slotId, getBeanById(id));
    }

    @Override
    public boolean removeBeanById(String slotId, U id) {
        return removeBean(slotId, getBeanById(id));
    }

    @Override
    public boolean moveToEndById(String slotId, U id) {
        return moveToEnd(slotId, getBeanById(id));
    }

    @Override
    public boolean addBean(String slotId, T t) {
        if(!checkSlotAndStatus(slotId, t)) {
            return false;
        }
        String redisKey = FLSQD_ASSIGN_ + getType() + "_" + slotId;
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
    public boolean removeBean(String slotId, T t) {
        if(!checkSlotId(slotId)) {
            return false;
        }
        String redisKey = FLSQD_ASSIGN_ + getType() + "_" + slotId;
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
    public boolean moveToEnd(String slotId, T t) {
        if(!checkSlotId(slotId)) {
            return false;
        }
        String redisKey = FLSQD_ASSIGN_ + getType() + "_" + slotId;
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

    public boolean beanChangeSlot(String preSlotId, String slotId, U id){
        if (checkSlotId(preSlotId)) {
            removeBeanById(preSlotId, id);
        }

        if (checkSlotId(slotId)) {
            addBeanById(slotId, id);
        }

        return true;
    }




    /**
     * 用于区分是岗位还是机器，或者以后增加的其他种类
     * @return
     */
    public abstract String getType();

    public abstract List<T> getBeanListFromDB(String slotId);

    public abstract T getBeanById(U Id);

    public boolean checkStatus(T t) {
        return true;
    }

    public boolean checkSlotId(String slotId){
        return true;
    }

    public boolean checkSlotAndStatus(String slotId, T t) {
        return checkSlotId(slotId) && checkStatus(t);
    }



}
