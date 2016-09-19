package net.blf2.model.dao.interfaces;

import net.blf2.model.entity.RepairForm;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/24.
 */
public interface IRepairFormDao extends IBaseDao<RepairForm> {
    public RepairForm queryDataByRepairId(String repairId);
    public List<RepairForm> queryDataByRepairerId(String repairerId);
    public List<RepairForm> queryDataByClassificationId(String classificationId);
    public List<RepairForm> queryDataByDescriptionTitle(String descriptionTitle);
    public List<RepairForm> queryDataByRepairPriority();
    public List<RepairForm> queryDataByCurrentStatus(Integer currentStatus);
}
