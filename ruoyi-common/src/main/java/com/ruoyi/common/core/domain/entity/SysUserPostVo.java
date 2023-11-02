package com.ruoyi.common.core.domain.entity;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2023-11-01 16:44
 */
public class SysUserPostVo extends SysUser{
     private String postCode;
     private String postName;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
