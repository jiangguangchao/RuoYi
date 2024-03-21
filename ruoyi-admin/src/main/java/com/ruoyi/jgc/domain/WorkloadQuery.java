package com.ruoyi.jgc.domain;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-03-21 11:31
 */
public class WorkloadQuery {
    private Long czr;
    private String beginTime;
    private String endTime;

    public Long getCzr() {
        return czr;
    }

    public void setCzr(Long czr) {
        this.czr = czr;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
