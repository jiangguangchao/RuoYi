package com.ruoyi.jgc.event.listener;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.jgc.service.IAssignWorkService;
import com.ruoyi.jgc.service.impl.AssignUserAtPostServiceImpl;
import com.ruoyi.jgc.service.impl.FlsqdServiceImpl;
import com.ruoyi.system.event.domain.UserStatusEvent;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-16 10:08
 */

@Component
public class UserStatusEventListener implements ApplicationListener<UserStatusEvent> {

    private static final Logger log = LoggerFactory.getLogger(UserStatusEventListener.class);

    @Autowired
    private AssignUserAtPostServiceImpl assignUserAtPostService;
    @Autowired
    private ISysUserService sysUserService;

    @Override
    public void onApplicationEvent(UserStatusEvent event) {

        SysUser user = sysUserService.selectUserById(event.getUserId());
        log.info("查询到用户 {}", JSON.toJSONString(user));
        if ("2".equals(user.getDelFlag())) {
            //用户被删除
            log.info("用户[{}]被删除，删除前所在岗位[{}]", event.getUserId(), event.getPostCode());
            assignUserAtPostService.removeBeanById(event.getPostCode(), event.getUserId());
            return;
        }

        log.info("用户[{}] 在岗位[{}]状态发生改变 [{} {}]", event.getUserId(), event.getPostCode(), event.getStatus(), event.getAssignWork());
        if ("0".equals(event.getStatus()) && "1".equals(event.getAssignWork())) {
            assignUserAtPostService.addBeanById(event.getPostCode(), event.getUserId());
        } else {
            assignUserAtPostService.removeBeanById(event.getPostCode(), event.getUserId());
        }
    }
}
