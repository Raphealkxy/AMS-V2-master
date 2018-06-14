package com.eric.modules.ams.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 考勤详情表
 * @author 
 */
@Table(name="ams_attdetall")
public class AmsAttdetallEntity implements Serializable {
    /**
     * 考勤详情ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attdetallId;

    /**
     * 考勤单ID
     */
    @NotEmpty
    private Long attlistId;

    /**
     * 学生姓名
     */
    private String userName;

    /**
     * 学号
     */
    @NotEmpty
    private String loginNum;

    /**
     * 出席状态 1-出席 2-未出席 3-请假
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Long getAttdetallId() {
        return attdetallId;
    }

    public void setAttdetallId(Long attdetallId) {
        this.attdetallId = attdetallId;
    }

    public Long getAttlistId() {
        return attlistId;
    }

    public void setAttlistId(Long attlistId) {
        this.attlistId = attlistId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(String loginNum) {
        this.loginNum = loginNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AmsAttdetallEntity other = (AmsAttdetallEntity) that;
        return (this.getAttdetallId() == null ? other.getAttdetallId() == null : this.getAttdetallId().equals(other.getAttdetallId()))
            && (this.getAttlistId() == null ? other.getAttlistId() == null : this.getAttlistId().equals(other.getAttlistId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getLoginNum() == null ? other.getLoginNum() == null : this.getLoginNum().equals(other.getLoginNum()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAttdetallId() == null) ? 0 : getAttdetallId().hashCode());
        result = prime * result + ((getAttlistId() == null) ? 0 : getAttlistId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getLoginNum() == null) ? 0 : getLoginNum().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", attdetallId=").append(attdetallId);
        sb.append(", attlistId=").append(attlistId);
        sb.append(", userName=").append(userName);
        sb.append(", loginNum=").append(loginNum);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}