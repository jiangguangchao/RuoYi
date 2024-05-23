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
    private String treatStatus;//这个是指整个疗程的治疗状态
    private Integer cureCount;
    private Integer curedCount;

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

    public String getTreatStatus() {
        return treatStatus;
    }

    public void setTreatStatus(String treatStatus) {
        this.treatStatus = treatStatus;
    }

    public Integer getCureCount() {
        return cureCount;
    }

    public void setCureCount(Integer cureCount) {
        this.cureCount = cureCount;
    }

    public Integer getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(Integer curedCount) {
        this.curedCount = curedCount;
    }
}
