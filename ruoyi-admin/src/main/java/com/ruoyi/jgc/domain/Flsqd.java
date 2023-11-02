package com.ruoyi.jgc.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 放疗申请单对象 flsqd
 *
 * @author jgc
 * @date 2023-10-31
 */
public class Flsqd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "ID")
    private String id;

    /** 患者姓名 */
    @Excel(name = "患者姓名")
    private String hzXm;

    /** 患者性别 */
    @Excel(name = "患者性别")
    private String hzXb;

    /** 患者出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "患者出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hzSr;

    /** 患者电话 */
    @Excel(name = "患者电话")
    private String hzDh;

    /** 患者住院号 */
    @Excel(name = "患者住院号")
    private String hzZyh;

    /** 患者科室 */
    @Excel(name = "患者科室")
    private String hzKs;

    /** 患者病房医生 */
    @Excel(name = "患者病房医生")
    private String hzBfys;

    /** 患者放疗医生 */
    @Excel(name = "患者放疗医生")
    private String hzFlys;

    /** 患者同步化疗 */
    @Excel(name = "患者同步化疗")
    private String hzTbhl;

    /** 患者病史/诊断 */
    @Excel(name = "患者病史/诊断")
    private String hzBs;

    /** 固定方式 */
    @Excel(name = "固定方式")
    private String gdfs;

    /** 扫描上界 */
    @Excel(name = "扫描上界")
    private String smsj;

    /** 扫描中心 */
    @Excel(name = "扫描中心")
    private String smzx;

    /** 扫描下界 */
    @Excel(name = "扫描下界")
    private String smxj;

    /** 层厚 */
    @Excel(name = "层厚")
    private Long ch;

    /** 扫描类型1 */
    @Excel(name = "扫描类型1")
    private String smlx1;

    /** 扫描类型2 */
    @Excel(name = "扫描类型2")
    private String smlx2;

    /** 是否空腹 */
    @Excel(name = "是否空腹")
    private String kf;

    /** 喝水毫升数 */
    @Excel(name = "喝水毫升数")
    private Long hes;

    /** 是否憋尿 */
    @Excel(name = "是否憋尿")
    private String bn;

    /** 是否铅丝标记 */
    @Excel(name = "是否铅丝标记")
    private String qsbj;

    /** 是否BOLUS */
    @Excel(name = "是否BOLUS")
    private String bolus;

    /** 是否口含瓶 */
    @Excel(name = "是否口含瓶")
    private String khp;

    /** 其它 */
    @Excel(name = "其它")
    private String qit;

    /** 图像融合类型 */
    @Excel(name = "图像融合类型")
    private String txrhlx;

    /** 图像融合编号 */
    @Excel(name = "图像融合编号")
    private String txrhbh;

    /** 是否重定位 */
    @Excel(name = "是否重定位")
    private String cdw;

    /** 治疗技术 */
    @Excel(name = "治疗技术")
    private String zljs;

    /** 治疗机器 */
    @Excel(name = "治疗机器")
    private String zljq;

    /** IGRT */
    @Excel(name = "IGRT")
    private String igrt;

    /** IGRT频率类型 */
    @Excel(name = "IGRT频率类型")
    private String igrtpllx;

    /** IGRT频率值 */
    @Excel(name = "IGRT频率值")
    private String igrtplz;

    /** 是否呼吸门控 */
    @Excel(name = "是否呼吸门控")
    private String hxmk;

    /** 是否剂量验证 */
    @Excel(name = "是否剂量验证")
    private String jlyz;

    /** 当前流程节点 */
    @Excel(name = "当前流程节点")
    private String dqlcjdmc;

    /** 当前所属操作人员 */
    @Excel(name = "当前所属操作人员")
    private Long dqczry;

    /** 放疗单状态 */
    @Excel(name = "放疗单状态")
    private String fldzt;

    /** 初始放疗单 */
    @Excel(name = "初始放疗单")
    private String fuid;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setHzXm(String hzXm)
    {
        this.hzXm = hzXm;
    }

    public String getHzXm()
    {
        return hzXm;
    }
    public void setHzXb(String hzXb)
    {
        this.hzXb = hzXb;
    }

    public String getHzXb()
    {
        return hzXb;
    }
    public void setHzSr(Date hzSr)
    {
        this.hzSr = hzSr;
    }

    public Date getHzSr()
    {
        return hzSr;
    }
    public void setHzDh(String hzDh)
    {
        this.hzDh = hzDh;
    }

    public String getHzDh()
    {
        return hzDh;
    }
    public void setHzZyh(String hzZyh)
    {
        this.hzZyh = hzZyh;
    }

    public String getHzZyh()
    {
        return hzZyh;
    }
    public void setHzKs(String hzKs)
    {
        this.hzKs = hzKs;
    }

    public String getHzKs()
    {
        return hzKs;
    }
    public void setHzBfys(String hzBfys)
    {
        this.hzBfys = hzBfys;
    }

    public String getHzBfys()
    {
        return hzBfys;
    }
    public void setHzFlys(String hzFlys)
    {
        this.hzFlys = hzFlys;
    }

    public String getHzFlys()
    {
        return hzFlys;
    }
    public void setHzTbhl(String hzTbhl)
    {
        this.hzTbhl = hzTbhl;
    }

    public String getHzTbhl()
    {
        return hzTbhl;
    }
    public void setHzBs(String hzBs)
    {
        this.hzBs = hzBs;
    }

    public String getHzBs()
    {
        return hzBs;
    }
    public void setGdfs(String gdfs)
    {
        this.gdfs = gdfs;
    }

    public String getGdfs()
    {
        return gdfs;
    }
    public void setSmsj(String smsj)
    {
        this.smsj = smsj;
    }

    public String getSmsj()
    {
        return smsj;
    }
    public void setSmzx(String smzx)
    {
        this.smzx = smzx;
    }

    public String getSmzx()
    {
        return smzx;
    }
    public void setSmxj(String smxj)
    {
        this.smxj = smxj;
    }

    public String getSmxj()
    {
        return smxj;
    }
    public void setCh(Long ch)
    {
        this.ch = ch;
    }

    public Long getCh()
    {
        return ch;
    }
    public void setSmlx1(String smlx1)
    {
        this.smlx1 = smlx1;
    }

    public String getSmlx1()
    {
        return smlx1;
    }
    public void setSmlx2(String smlx2)
    {
        this.smlx2 = smlx2;
    }

    public String getSmlx2()
    {
        return smlx2;
    }
    public void setKf(String kf)
    {
        this.kf = kf;
    }

    public String getKf()
    {
        return kf;
    }
    public void setHes(Long hes)
    {
        this.hes = hes;
    }

    public Long getHes()
    {
        return hes;
    }
    public void setBn(String bn)
    {
        this.bn = bn;
    }

    public String getBn()
    {
        return bn;
    }
    public void setQsbj(String qsbj)
    {
        this.qsbj = qsbj;
    }

    public String getQsbj()
    {
        return qsbj;
    }
    public void setBolus(String bolus)
    {
        this.bolus = bolus;
    }

    public String getBolus()
    {
        return bolus;
    }
    public void setKhp(String khp)
    {
        this.khp = khp;
    }

    public String getKhp()
    {
        return khp;
    }
    public void setQit(String qit)
    {
        this.qit = qit;
    }

    public String getQit()
    {
        return qit;
    }
    public void setTxrhlx(String txrhlx)
    {
        this.txrhlx = txrhlx;
    }

    public String getTxrhlx()
    {
        return txrhlx;
    }
    public void setTxrhbh(String txrhbh)
    {
        this.txrhbh = txrhbh;
    }

    public String getTxrhbh()
    {
        return txrhbh;
    }
    public void setCdw(String cdw)
    {
        this.cdw = cdw;
    }

    public String getCdw()
    {
        return cdw;
    }
    public void setZljs(String zljs)
    {
        this.zljs = zljs;
    }

    public String getZljs()
    {
        return zljs;
    }
    public void setZljq(String zljq)
    {
        this.zljq = zljq;
    }

    public String getZljq()
    {
        return zljq;
    }
    public void setIgrt(String igrt)
    {
        this.igrt = igrt;
    }

    public String getIgrt()
    {
        return igrt;
    }
    public void setIgrtpllx(String igrtpllx)
    {
        this.igrtpllx = igrtpllx;
    }

    public String getIgrtpllx()
    {
        return igrtpllx;
    }
    public void setIgrtplz(String igrtplz)
    {
        this.igrtplz = igrtplz;
    }

    public String getIgrtplz()
    {
        return igrtplz;
    }
    public void setHxmk(String hxmk)
    {
        this.hxmk = hxmk;
    }

    public String getHxmk()
    {
        return hxmk;
    }
    public void setJlyz(String jlyz)
    {
        this.jlyz = jlyz;
    }

    public String getJlyz()
    {
        return jlyz;
    }
    public void setDqlcjdmc(String dqlcjdmc)
    {
        this.dqlcjdmc = dqlcjdmc;
    }

    public String getDqlcjdmc()
    {
        return dqlcjdmc;
    }
    public void setDqczry(Long dqczry)
    {
        this.dqczry = dqczry;
    }

    public Long getDqczry()
    {
        return dqczry;
    }
    public void setFldzt(String fldzt)
    {
        this.fldzt = fldzt;
    }

    public String getFldzt()
    {
        return fldzt;
    }
    public void setFuid(String fuid)
    {
        this.fuid = fuid;
    }

    public String getFuid()
    {
        return fuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("hzXm", getHzXm())
                .append("hzXb", getHzXb())
                .append("hzSr", getHzSr())
                .append("hzDh", getHzDh())
                .append("hzZyh", getHzZyh())
                .append("hzKs", getHzKs())
                .append("hzBfys", getHzBfys())
                .append("hzFlys", getHzFlys())
                .append("hzTbhl", getHzTbhl())
                .append("hzBs", getHzBs())
                .append("gdfs", getGdfs())
                .append("smsj", getSmsj())
                .append("smzx", getSmzx())
                .append("smxj", getSmxj())
                .append("ch", getCh())
                .append("smlx1", getSmlx1())
                .append("smlx2", getSmlx2())
                .append("kf", getKf())
                .append("hes", getHes())
                .append("bn", getBn())
                .append("qsbj", getQsbj())
                .append("bolus", getBolus())
                .append("khp", getKhp())
                .append("qit", getQit())
                .append("txrhlx", getTxrhlx())
                .append("txrhbh", getTxrhbh())
                .append("cdw", getCdw())
                .append("zljs", getZljs())
                .append("zljq", getZljq())
                .append("igrt", getIgrt())
                .append("igrtpllx", getIgrtpllx())
                .append("igrtplz", getIgrtplz())
                .append("hxmk", getHxmk())
                .append("jlyz", getJlyz())
                .append("dqlcjdmc", getDqlcjdmc())
                .append("dqczry", getDqczry())
                .append("fldzt", getFldzt())
                .append("fuid", getFuid())
                .toString();
    }
}
