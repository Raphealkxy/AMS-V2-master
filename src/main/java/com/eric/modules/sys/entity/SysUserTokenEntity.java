package com.eric.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * token实体类
 * @author Chen 2018/1/9
 */
@Table(name="sys_user_token")
public class SysUserTokenEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long userId;

    /**
     * token
     */
    @NotEmpty
    private String token;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}