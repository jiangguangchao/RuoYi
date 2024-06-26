package com.ruoyi.system.event.domain;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-16 10:01
 */
public class UserStatusEvent extends ApplicationEvent {

    private Long userId;
    private String status;
    private String assignWork;
    private List<String> postCode;



    public UserStatusEvent(Object source,Long userId, String status, String assignWork, List<String> postCode) {
        super(source);
        this.userId = userId;
        this.status = status;
        this.assignWork = assignWork;
        this.postCode = postCode;
    }

    public Long getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignWork() {
        return assignWork;
    }

    public List<String> getPostCode() {
        return postCode;
    }
}
