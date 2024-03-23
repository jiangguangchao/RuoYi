package com.ruoyi.jgc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 放疗机器对象 machine
 *
 * @author ruoyi
 * @date 2024-03-23
 */
public class Machine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 机器种类 */
    @Excel(name = "机器种类")
    private String type;

    /** 机器名称 */
    @Excel(name = "机器名称")
    private String name;

    /** 工作状态 0--不工作 1--工作中 */
    @Excel(name = "工作状态 0--不工作 1--工作中")
    private String workState;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setWorkState(String workState)
    {
        this.workState = workState;
    }

    public String getWorkState()
    {
        return workState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("type", getType())
                .append("name", getName())
                .append("workState", getWorkState())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
