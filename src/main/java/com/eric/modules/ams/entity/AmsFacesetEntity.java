package com.eric.modules.ams.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 脸集实体类
 * @author 
 */
@Table(name="ams_faceset")
public class AmsFacesetEntity implements Serializable {
    /**
     * 用户提供的FaceSet标识outer_id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facesetId;

    /**
     * 主键
     */
    @NotEmpty
    private String facesetToken;

    /**
     * faceSet名称
     */
    private String displayName;

    /**
     * 标签
     */
    private String tags;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门ID
     */
    private Long deptId;

    private static final long serialVersionUID = 1L;

    public Long getFacesetId() {
        return facesetId;
    }

    public void setFacesetId(Long facesetId) {
        this.facesetId = facesetId;
    }

    public String getFacesetToken() {
        return facesetToken;
    }

    public void setFacesetToken(String facesetToken) {
        this.facesetToken = facesetToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
        AmsFacesetEntity other = (AmsFacesetEntity) that;
        return (this.getFacesetId() == null ? other.getFacesetId() == null : this.getFacesetId().equals(other.getFacesetId()))
            && (this.getFacesetToken() == null ? other.getFacesetToken() == null : this.getFacesetToken().equals(other.getFacesetToken()))
            && (this.getDisplayName() == null ? other.getDisplayName() == null : this.getDisplayName().equals(other.getDisplayName()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
            && (this.getDeptName() == null ? other.getDeptName() == null : this.getDeptName().equals(other.getDeptName()))
            && (this.getDeptId() == null ? other.getDeptId() == null : this.getDeptId().equals(other.getDeptId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFacesetId() == null) ? 0 : getFacesetId().hashCode());
        result = prime * result + ((getFacesetToken() == null) ? 0 : getFacesetToken().hashCode());
        result = prime * result + ((getDisplayName() == null) ? 0 : getDisplayName().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getDeptName() == null) ? 0 : getDeptName().hashCode());
        result = prime * result + ((getDeptId() == null) ? 0 : getDeptId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", facesetId=").append(facesetId);
        sb.append(", facesetToken=").append(facesetToken);
        sb.append(", displayName=").append(displayName);
        sb.append(", tags=").append(tags);
        sb.append(", deptName=").append(deptName);
        sb.append(", deptId=").append(deptId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}