package net.blf2.model.dao.interfaces;

import net.blf2.model.entity.RepairComment;

import java.util.List;

/**
 * Created by blf2 on 16-9-17.
 */
public interface IRepairCommentDao extends IBaseDao<RepairComment>{
    RepairComment queryRepairCommentById(String repairCommentId);
    List<RepairComment> queryCommentByRepairId(String repairId);
}
