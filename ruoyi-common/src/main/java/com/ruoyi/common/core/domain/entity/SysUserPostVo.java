package com.ruoyi.common.core.domain.entity;

import java.util.Objects;

/**
 * @program: ruoyi
 * @description:
 * @author:
 * @create: 2023-11-01 16:44
 */
public class SysUserPostVo extends SysUser{
     private String postCode;
     private String postName;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SysUserPostVo user = (SysUserPostVo) obj;
        return Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }


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
