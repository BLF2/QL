package net.blf2.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Entity
@Table(name="ScheduleInfo")
public class ScheduleInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String scheduleId;//跟踪表单流水号
    @Column(name="handlerUserId")
    private String handlerUserId;//处理人员ID
    @Column(name="repairId")
    private String repairId;//维修单ID
    @Column(name = "scheuleStatus")
    private String scheuleStatus;//当前状态
    @Column(name="changeTime")
    private Date changeTime;//修改时间

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getHandlerUserId() {
        return handlerUserId;
    }

    public void setHandlerUserId(String handlerUserId) {
        this.handlerUserId = handlerUserId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getScheuleStatus() {
        return scheuleStatus;
    }

    public void setScheuleStatus(String scheuleStatus) {
        this.scheuleStatus = scheuleStatus;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
}
