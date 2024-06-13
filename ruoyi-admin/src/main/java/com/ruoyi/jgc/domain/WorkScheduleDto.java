package com.ruoyi.jgc.domain;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-05-25 09:58
 */
public class WorkScheduleDto extends WorkSchedule{
    private String userName;
    private String dbrName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDbrName() {
        return dbrName;
    }

    public void setDbrName(String dbrName) {
        this.dbrName = dbrName;
    }
}
