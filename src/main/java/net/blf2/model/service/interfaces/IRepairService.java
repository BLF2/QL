package net.blf2.model.service.interfaces;

import net.blf2.model.entity.RepairForm;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/27.
 */
public interface IRepairService {
    public RepairForm addNewRepairForm(RepairForm repairForm);
    public Boolean updateRepairForm(RepairForm repairForm);
    public Boolean deleteRepairForm(RepairForm repairForm);
    public Boolean deleteRepairFormByRepairId(String repairId);
    public Boolean deleteRepairFormByRepairerId(String repairerId);
    public Boolean deleteRepairFormByClassificationId(String classificationId);
    public RepairForm queryRepairFormByRepairId(String repairId);
    public List<RepairForm> queryRepairFormByRepairerId(String repairerId);
    public List<RepairForm> queryRepairFormByClassificationId(String classificationId);
    public List<RepairForm> queryRepairFormByCurrentStatus(Integer currentStatus);
    public List<RepairForm> queryRepairFormAll();
    public List<RepairForm> queryRepairFormByDescriptionTitle(String descriptionTitle);
    public List<RepairForm> queryRepairFormByRepairPriority();
}
