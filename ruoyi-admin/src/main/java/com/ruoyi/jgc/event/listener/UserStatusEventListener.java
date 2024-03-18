package com.ruoyi.jgc.event.listener;

import com.ruoyi.jgc.service.IAssignWorkService;
import com.ruoyi.jgc.service.impl.FlsqdServiceImpl;
import com.ruoyi.system.event.domain.UserStatusEvent;
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
    private IAssignWorkService assignWorkService;

    @Override
    public void onApplicationEvent(UserStatusEvent event) {
        String postCode = event.getPostCode();
        Long userId = event.getUserId();
        if (StringUtils.isEmpty(postCode) ) {
            log.info("用户状态发生变化，但用户现岗位为空，不做岗位分配序列处理");
            return;
        }
        String[] posts = FlsqdServiceImpl.lcArr;
        if (!ArrayUtils.contains(posts, postCode)) {
            log.info("用户状态发生变化，但当前用户[{}]所在岗位[{}]不属于放疗单相关岗位角色，不做岗位分配序列处理",
                    userId, postCode);
            return;
        }
        if ("0".equals(event.getStatus()) && "1".equals(event.getAssignWork())) {
            assignWorkService.addUserAtPost(event.getPostCode(), event.getUserId());
        } else {
            assignWorkService.removeUserAtPost(event.getPostCode(), event.getUserId());
        }
    }
}
