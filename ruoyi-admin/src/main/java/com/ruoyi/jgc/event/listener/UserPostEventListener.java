package com.ruoyi.jgc.event.listener;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.jgc.service.IAssignWorkService;
import com.ruoyi.jgc.service.impl.AssignUserAtPostServiceImpl;
import com.ruoyi.system.event.domain.UserPostEvent;
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
    private IAssignWorkService assignWorkService;
    @Autowired
    private AssignUserAtPostServiceImpl assignUserAtPostService;

    @Override
    public void onApplicationEvent(UserPostEvent event) {
        String postCode = event.getPostCode();
        String prePostCode = event.getPrePostCode();
        if (StringUtils.isEmpty(postCode) && StringUtils.isEmpty(prePostCode)) {
            return;
        }

        // assignWorkService.userPostChange(event.getUserId(), event.getPostCode(), event.getPrePostCode());
        assignUserAtPostService.beanChangeSlot(event.getPrePostCode(), event.getPostCode(), event.getUserId());
    }
}
