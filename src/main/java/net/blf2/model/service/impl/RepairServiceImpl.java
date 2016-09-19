package net.blf2.model.service.impl;

import net.blf2.model.dao.interfaces.IRepairFormDao;
import net.blf2.model.dao.interfaces.IScheduleInfoDao;
import net.blf2.model.entity.RepairForm;
import net.blf2.model.entity.ScheduleInfo;
import net.blf2.model.service.interfaces.IRepairService;
import net.blf2.util.GlobalStaticTools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by t_mengxh on 2016/7/30.
 */
@Service("RepairServiceImpl")
public class RepairServiceImpl implements IRepairService{
    @Autowired
    @Qualifier("RepairFormDaoImpl")
    private IRepairFormDao iRepairFormDao;

    @Autowired
    @Qualifier("ScheduleInfoDaoImpl")
    private IScheduleInfoDao scheduleInfoDao;

    public IScheduleInfoDao getScheduleInfoDao() {
        return scheduleInfoDao;
    }

    public void setScheduleInfoDao(IScheduleInfoDao scheduleInfoDao) {
        this.scheduleInfoDao = scheduleInfoDao;
    }

    private static Logger logger = Logger.getLogger(RepairServiceImpl.class);

    public IRepairFormDao getiRepairFormDao() {
        return iRepairFormDao;
    }

    public void setiRepairFormDao(IRepairFormDao iRepairFormDao) {
        this.iRepairFormDao = iRepairFormDao;
    }

    public RepairForm addNewRepairForm(RepairForm repairForm) {
        //repairForm.setDescriptionText(StringEscapeUtils.escapeEcmaScript(repairForm.getDescriptionText()));
        //repairForm.setDescriptionTitle(StringEscapeUtils.escapeEcmaScript(repairForm.getDescriptionTitle()));
        return iRepairFormDao.insertData(repairForm);
    }

    public Boolean updateRepairForm(RepairForm repairForm) {
     //   repairForm.setDescriptionText(StringEscapeUtils.escapeEcmaScript(repairForm.getDescriptionText()));
      // repairForm.setDescriptionTitle(StringEscapeUtils.escapeEcmaScript(repairForm.getDescriptionTitle()));
        return iRepairFormDao.updateData(repairForm);
    }

    public Boolean deleteRepairForm(RepairForm repairForm) {
        if(repairForm == null){
            logger.warn("In deleteRepairForm repairForm is null");
            return Boolean.FALSE;
        }
        return cleanRepairFormRelevantInfo(repairForm) && iRepairFormDao.deleteData(repairForm);
    }

    public Boolean deleteRepairFormByRepairId(String repairId) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(repairId)) {
            logger.error("In deleteRepairFormByRepairId repairId is null");
            return Boolean.FALSE;
        }
        RepairForm repairForm = this.queryRepairFormByRepairId(repairId);
        return repairForm == null ? null : cleanRepairFormRelevantInfo(repairForm) && iRepairFormDao.deleteData(repairForm);
    }
    private Boolean cleanRepairFormRelevantInfo(RepairForm repairForm){
        List<ScheduleInfo> scheduleInfoList = scheduleInfoDao.queryDataByRepairId(repairForm.getRepairId());
        if(scheduleInfoList == null)
            return Boolean.TRUE;
        Iterator<ScheduleInfo>iterator = scheduleInfoList.iterator();
        while(iterator.hasNext()){
            ScheduleInfo scheduleInfo = iterator.next();
            scheduleInfoDao.deleteData(scheduleInfo);
        }
        return Boolean.TRUE;
    }
    public Boolean deleteRepairFormByRepairerId(String repairerId) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(repairerId)) {
            logger.warn("In deleteRepairFormByRepairerId repairerId is null");
            return Boolean.FALSE;
        }
        List<RepairForm>repairFormList = this.queryRepairFormByRepairerId(repairerId);
        if(repairFormList == null) {
            logger.warn("In deleteRepairFormByRepairerId there hava no records in DB");
            return Boolean.FALSE;
        }
        Iterator<RepairForm>iter = repairFormList.iterator();
        while (iter.hasNext()){
            this.deleteRepairForm(iter.next());
        }
        return Boolean.TRUE;
    }

    public Boolean deleteRepairFormByClassificationId(String classificationId) {

        return null;
    }

    public RepairForm queryRepairFormByRepairId(String repairId) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(repairId)){
            logger.warn("In queryRepairFormByRepairId repairId is null");
            return null;
        }
        return iRepairFormDao.queryDataByRepairId(repairId);
    }

    public List<RepairForm> queryRepairFormByRepairerId(String repairerId) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(repairerId)){
            logger.warn("In queryRepairFormByRepairerId repairerId is null");
            return null;
        }
        return iRepairFormDao.queryDataByRepairerId(repairerId);
    }

    public List<RepairForm> queryRepairFormByClassificationId(String classificationId) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(classificationId)) {
            logger.warn("In queryRepairFormByClassificationId classificationId is null");
            return null;
        }
        return iRepairFormDao.queryDataByClassificationId(classificationId);
    }

    public List<RepairForm> queryRepairFormByCurrentStatus(Integer currentStatus) {
        return iRepairFormDao.queryDataByCurrentStatus(currentStatus);
    }

    public List<RepairForm> queryRepairFormAll() {
        return iRepairFormDao.queryDataAll();
    }

    public List<RepairForm> queryRepairFormByDescriptionTitle(String descriptionTitle) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(descriptionTitle)){
            logger.warn("In queryRepairFormByDescriptionTitle descriptionTitle is null");
            return null;
        }
     //   descriptionTitle = StringEscapeUtils.escapeEcmaScript(descriptionTitle);
        return iRepairFormDao.queryDataByDescriptionTitle(descriptionTitle);
    }

    public List<RepairForm> queryRepairFormByRepairPriority() {
        return iRepairFormDao.queryDataByRepairPriority();
    }
}
