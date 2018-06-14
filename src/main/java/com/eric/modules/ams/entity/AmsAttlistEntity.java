package com.eric.modules.ams.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 考勤单表
 * @author 
 */
@Table(name="ams_attlist")
public class AmsAttlistEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 考勤单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attId;

    /**
     * 考勤单编号
     */
    @NotEmpty
    private String attCode;

    /**
     * 课程ID
     */
    @NotEmpty
    private Long courseId;

    /**
     * 课程名
     */
    @NotEmpty
    private String courseName;

    /**
     * 学期
     */
    private String termNum;

    /**
     * 创建人ID
     */
    @NotEmpty
    private Long createId;

    /**
     * 更新人ID
     */
    private Long updateId;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 识别状态，1-上传成功，2正在识别，3，识别完成，4失败
     */
    private Integer status;

    @Transient
    private Integer attStatus;


    public Long getAttId() {
        return attId;
    }

    public void setAttId(Long attId) {
        this.attId = attId;
    }

    public String getAttCode() {
        return attCode;
    }

    public void setAttCode(String attCode) {
        this.attCode = attCode;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTermNum() {
        return termNum;
    }

    public void setTermNum(String termNum) {
        this.termNum = termNum;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(Integer attStatus) {
        this.attStatus = attStatus;
    }
}