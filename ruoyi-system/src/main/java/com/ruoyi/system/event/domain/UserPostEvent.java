package com.ruoyi.system.event.domain;

import java.util.List;

import org.springframework.context.ApplicationEvent;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-16 17:19
 */
public class UserPostEvent extends ApplicationEvent {

    private Long userId;

    /**
     * 说明：postCode标识当前所在岗位，prePostCode标识原来的岗位
     * 如果prePostCode和postCode一致，说明岗位没有变化，如果不一致，
     * 说明岗位发生变化
     */
    private List<String> postCode;
    private List<String> prePostCode;

    public UserPostEvent(Object source, Long userId, List<String> postCode, List<String> prePostCode) {
        super(source);
        this.userId = userId;
        this.postCode = postCode;
        this.prePostCode = prePostCode;
    }

    public Long getUserId() {
        return userId;
    }

    public List<String> getPostCode() {
        return postCode;
    }

    public List<String> getPrePostCode() {
        return prePostCode;
    }
}
