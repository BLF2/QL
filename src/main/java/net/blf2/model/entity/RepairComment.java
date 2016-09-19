package net.blf2.model.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by blf2 on 16-9-17.
 */

@Entity
@Table(name = "RepairComment")
public class RepairComment {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String repairCommentId;//评论ID
    @Column(name = "repairId")
    private String repairId;//维修ID
    @Column(name = "commentText",length = 1024)
    private String commentText;//评论内容
    @Column(name = "commentDateTime")
    private Date commentDateTime;//评论时间
    @Column(name = "submiterId")
    private String submiterId;//评论人ID

    public String getRepairCommentId() {
        return repairCommentId;
    }

    public void setRepairCommentId(String repairCommentId) {
        this.repairCommentId = repairCommentId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentDateTime() {
        return commentDateTime;
    }

    public void setCommentDateTime(Date commentDateTime) {
        this.commentDateTime = commentDateTime;
    }

    public String getSubmiterId() {
        return submiterId;
    }

    public void setSubmiterId(String submiterId) {
        this.submiterId = submiterId;
    }
}
