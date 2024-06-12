package com.ruoyi.jgc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 器官危害对象 organdamage
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
public class Organdamage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 放疗单编号 */
    @Excel(name = "放疗单编号")
    private String fldid;

    /** 器官 */
    @Excel(name = "器官")
    private String organ;

    /** 限制剂量 */
    @Excel(name = "限制剂量")
    private Long doselimit;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFldid(String fldid) 
    {
        this.fldid = fldid;
    }

    public String getFldid() 
    {
        return fldid;
    }
    public void setOrgan(String organ) 
    {
        this.organ = organ;
    }

    public String getOrgan() 
    {
        return organ;
    }
    public void setDoselimit(Long doselimit) 
    {
        this.doselimit = doselimit;
    }

    public Long getDoselimit() 
    {
        return doselimit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fldid", getFldid())
            .append("organ", getOrgan())
            .append("doselimit", getDoselimit())
            .toString();
    }
}
