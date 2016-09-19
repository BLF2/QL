package net.blf2.model.service.interfaces;

import net.blf2.model.entity.RepairComment;

import java.util.List;

/**
 * Created by blf2 on 16-9-17.
 */
public interface IRepairCommentService {
    public RepairComment insertRepairComment(RepairComment repairComment);
    public RepairComment queryRepairCommentById(String repairCommentId);
    public List<RepairComment> queryRepairCommentByRepairId(String repairId);
    public List<RepairComment> queryRepairCommentAll();
    public Boolean updateRepairComment(RepairComment repairComment);
    public Boolean deleteRepairComment(RepairComment repairComment);
}
