package com.ruoyi.jgc.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 排班对象 work_schedule
 * 
 * @author ruoyi
 * @date 2024-04-29
 */
public class WorkSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 值班日期 */
    private Date schDate;

    /** 值班时间 */
    private String schTime;

    /** 值班机器 */
    private Long machineId;

    /** 顶班人 */
    private Long dbr;

    /** 顶班时间 */
    private String dbsj;

    /** 用户输入内容 一般是周六可能需要手动填一些内容，而不是选择机器 */
    @Excel(name = "用户输入内容")
    private String textInput;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setSchDate(Date schDate) 
    {
        this.schDate = schDate;
    }

    public Date getSchDate() 
    {
        return schDate;
    }
    public void setSchTime(String schTime) 
    {
        this.schTime = schTime;
    }

    public String getSchTime() 
    {
        return schTime;
    }
    public void setMachineId(Long machineId) 
    {
        this.machineId = machineId;
    }

    public Long getMachineId() 
    {
        return machineId;
    }
    public void setDbr(Long dbr) 
    {
        this.dbr = dbr;
    }

    public Long getDbr() 
    {
        return dbr;
    }
    public void setDbsj(String dbsj) 
    {
        this.dbsj = dbsj;
    }

    public String getDbsj() 
    {
        return dbsj;
    }
    public void setTextInput(String textInput) 
    {
        this.textInput = textInput;
    }

    public String getTextInput() 
    {
        return textInput;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("schDate", getSchDate())
            .append("schTime", getSchTime())
            .append("machineId", getMachineId())
            .append("dbr", getDbr())
            .append("dbsj", getDbsj())
            .append("textInput", getTextInput())
            .toString();
    }
}
