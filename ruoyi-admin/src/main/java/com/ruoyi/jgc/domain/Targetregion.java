package com.ruoyi.jgc.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 靶区对象 targetregion
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
public class Targetregion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 放疗单编号 */
    @Excel(name = "放疗单编号")
    private String fldid;

    /** 靶区 */
    @Excel(name = "靶区")
    private String region;

    /** 分次剂量 */
    @Excel(name = "分次剂量")
    private Long dose;

    /** 分次数 */
    @Excel(name = "分次数")
    private Long count;

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
    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }
    public void setDose(Long dose) 
    {
        this.dose = dose;
    }

    public Long getDose() 
    {
        return dose;
    }
    public void setCount(Long count) 
    {
        this.count = count;
    }

    public Long getCount() 
    {
        return count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fldid", getFldid())
            .append("region", getRegion())
            .append("dose", getDose())
            .append("count", getCount())
            .toString();
    }
}
