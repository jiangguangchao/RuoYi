package com.ruoyi.jgc.domain;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2024-04-08 14:13
 */
public class RadiotherapyDto extends Radiotherapy{
    private String hzXm;
    private String machineName;

    public String getHzXm() {
        return hzXm;
    }

    public void setHzXm(String hzXm) {
        this.hzXm = hzXm;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}
