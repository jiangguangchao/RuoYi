package com.ruoyi.jgc.domain;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-21 14:15
 */
public class WorkloadDto {

    private Long userId;
    private String userName;
    private Integer workloadCount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getWorkloadCount() {
        return workloadCount;
    }

    public void setWorkloadCount(Integer workloadCount) {
        this.workloadCount = workloadCount;
    }
}
