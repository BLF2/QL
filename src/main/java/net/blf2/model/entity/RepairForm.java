package net.blf2.model.entity;



import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Entity
@Table(name="RepairForm")
public class RepairForm {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String repairId;//维修表单流水号
    @Column(name="userId")
    private String repairerId;//提交人员ID
    @Column(name="classificationId")
    private String classificationId;//分类ID
    @Column(name="descriptionTitle")
    private String descriptionTitle;//描述题目
    @Column(name="descriptionText",length = 5120)
    private String descriptionText;//问题描述
    @Column(name="imgePath",length = 1024)
    private String imgePath;//图片路径
    @Column(name="videoPath",length = 1024)
    private String videoPath;//视频路径
    @Column(name="submitTime")
    private Date submitTime;//提交时间
    @Column(name="repairPriority")
    private Integer repairPriority;//维修优先级
    @Column(name = "currentStatus")
    private Integer currentStatus;//当前状态 1 提交，可修改提交数据  0 不可修改提交数据
    @Column(name = "repairNote",length = 1024)
    private String repairNote;//维修备注


    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getRepairerId() {
        return repairerId;
    }

    public void setRepairerId(String repairerId) {
        this.repairerId = repairerId;
    }

    public String getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }

    public String getDescriptionTitle() {
        return descriptionTitle;
    }

    public void setDescriptionTitle(String descriptionTitle) {
        this.descriptionTitle = descriptionTitle;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getImgePath() {
        return imgePath;
    }

    public void setImgePath(String imgePath) {
        this.imgePath = imgePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getRepairPriority() {
        return repairPriority;
    }

    public void setRepairPriority(Integer repairPriority) {
        this.repairPriority = repairPriority;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getRepairNote() {
        return repairNote;
    }

    public void setRepairNote(String repairNote) {
        this.repairNote = repairNote;
    }
}
