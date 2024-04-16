package com.ruoyi.jgc.event.listener;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.jgc.service.IAssignWorkService;
import com.ruoyi.jgc.service.impl.AssignUserAtPostServiceImpl;
import com.ruoyi.system.event.domain.UserPostEvent;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-16 17:21
 */
@Component
public class UserPostEventListener implements ApplicationListener<UserPostEvent> {

    @Autowired
    private AssignUserAtPostServiceImpl assignUserAtPostService;

    @Override
    public void onApplicationEvent(UserPostEvent event) {
        List<String> postCode = event.getPostCode();
        List<String> prePostCode = event.getPrePostCode();
        if (CollectionUtils.isEmpty(postCode) && CollectionUtils.isEmpty(prePostCode)) {
            return;
        }

        assignUserAtPostService.beanChangeSlot(event.getPrePostCode(), event.getPostCode(), event.getUserId());
    }
}
