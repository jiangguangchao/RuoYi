package com.ruoyi.jgc.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 放疗流程记录对象 fllcjl
 *
 * @author ruoyi
 * @date 2023-11-04
 */
public class Fllcjl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 放疗申请单id */
    private String flid;

    /** $column.columnComment */
    private Long id;

    /** 流程节点 */
    @Excel(name = "流程节点")
    private String lcjdmc;

    /** 流程节点序号 */
    @Excel(name = "流程节点序号")
    private Integer lcjdxh;

    /** 操作人 */
    @Excel(name = "操作人")
    private Long czr;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date czsj;

    public void setFlid(String flid)
    {
        this.flid = flid;
    }

    public String getFlid()
    {
        return flid;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setLcjdmc(String lcjdmc)
    {
        this.lcjdmc = lcjdmc;
    }

    public String getLcjdmc()
    {
        return lcjdmc;
    }
    public void setLcjdxh(Integer lcjdxh)
    {
        this.lcjdxh = lcjdxh;
    }

    public Integer getLcjdxh()
    {
        return lcjdxh;
    }
    public void setCzr(Long czr)
    {
        this.czr = czr;
    }

    public Long getCzr()
    {
        return czr;
    }
    public void setCzsj(Date czsj)
    {
        this.czsj = czsj;
    }

    public Date getCzsj()
    {
        return czsj;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("flid", getFlid())
                .append("id", getId())
                .append("lcjdmc", getLcjdmc())
                .append("lcjdxh", getLcjdxh())
                .append("czr", getCzr())
                .append("czsj", getCzsj())
                .toString();
    }
}
