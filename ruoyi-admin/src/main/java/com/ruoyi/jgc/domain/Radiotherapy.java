package com.ruoyi.jgc.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 放射治疗对象 radiotherapy
 * 
 * @author jgc
 * @date 2024-04-07
 */
public class Radiotherapy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 放疗单编号 */
    @Excel(name = "放疗单编号")
    private String fldId;

    /** 机器编号 */
    @Excel(name = "机器编号")
    private Integer machineId;

    /** 是否已安排治疗时间 N-否 Y-是 */
    @Excel(name = "是否已安排治疗时间 N-否 Y-是")
    private String schFlag;

    /** 预计治疗时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "预计治疗时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date schTime;

    /** 是否已治疗 N-否 Y-是 */
    @Excel(name = "是否已治疗 N-否 Y-是")
    private String cureFlag;

    /** 治疗操作技师id，如果有多个，用逗号隔开 */
    @Excel(name = "治疗操作技师id，如果有多个，用逗号隔开")
    private String cureOperator;

    /** 治疗结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "治疗结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date cureEndTime;

    /** 治疗状态 0--未开始 1--治疗中 5--已结束 */
    @Excel(name = "治疗状态 0--未开始 1--治疗中 5--已结束")
    private String cureStatus;

    private Double x;
    private Double y;
    private Double z;
    private Double rotation;
    private Integer cureIndex;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFldId(String fldId) 
    {
        this.fldId = fldId;
    }

    public String getFldId() 
    {
        return fldId;
    }
    public void setMachineId(Integer machineId) 
    {
        this.machineId = machineId;
    }

    public Integer getMachineId() 
    {
        return machineId;
    }
    public void setSchFlag(String schFlag) 
    {
        this.schFlag = schFlag;
    }

    public String getSchFlag() 
    {
        return schFlag;
    }
    public void setSchTime(Date schTime) 
    {
        this.schTime = schTime;
    }

    public Date getSchTime() 
    {
        return schTime;
    }
    public void setCureFlag(String cureFlag) 
    {
        this.cureFlag = cureFlag;
    }

    public String getCureFlag() 
    {
        return cureFlag;
    }
    public void setCureOperator(String cureOperator) 
    {
        this.cureOperator = cureOperator;
    }

    public String getCureOperator() 
    {
        return cureOperator;
    }
    public void setCureEndTime(Date cureEndTime) 
    {
        this.cureEndTime = cureEndTime;
    }

    public Date getCureEndTime() 
    {
        return cureEndTime;
    }
    public void setCureStatus(String cureStatus) 
    {
        this.cureStatus = cureStatus;
    }

    public String getCureStatus() 
    {
        return cureStatus;
    }

    public Double getRotation() {
        return rotation;
    }

    public void setRotation(Double rotation) {
        this.rotation = rotation;
    }

    public Integer getCureIndex() {
        return cureIndex;
    }

    public void setCureIndex(Integer cureIndex) {
        this.cureIndex = cureIndex;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fldId", getFldId())
            .append("machineId", getMachineId())
            .append("schFlag", getSchFlag())
            .append("schTime", getSchTime())
            .append("cureFlag", getCureFlag())
            .append("cureOperator", getCureOperator())
            .append("cureEndTime", getCureEndTime())
            .append("cureStatus", getCureStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
