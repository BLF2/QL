package net.blf2.model.service.impl;

import net.blf2.model.dao.interfaces.IScheduleInfoDao;
import net.blf2.model.entity.ScheduleInfo;
import net.blf2.model.service.interfaces.IScheduleService;
import net.blf2.util.GlobalStaticTools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/30.
 */
@Service("ScheduleServiceImpl")
public class ScheduleServiceImpl implements IScheduleService {
    @Autowired
    @Qualifier("ScheduleInfoDaoImpl")
    private IScheduleInfoDao iScheduleInfoDao;
    private static Logger logger = Logger.getLogger(ScheduleServiceImpl.class);

    public IScheduleInfoDao getiScheduleInfoDao() {
        return iScheduleInfoDao;
    }

    public void setiScheduleInfoDao(IScheduleInfoDao iScheduleInfoDao) {
        this.iScheduleInfoDao = iScheduleInfoDao;
    }

    public ScheduleInfo addNewScheduleInfo(ScheduleInfo scheduleInfo) {
        return iScheduleInfoDao.insertData(scheduleInfo);
    }

    public Boolean updateScheduleInfo(ScheduleInfo scheduleInfo) {
        return iScheduleInfoDao.updateData(scheduleInfo);
    }

    public Boolean deleteScheduleInfo(ScheduleInfo scheduleInfo) {
        return iScheduleInfoDao.deleteData(scheduleInfo);
    }

    public ScheduleInfo queryScheduleInfoByScheduleId(String scheduleId) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(scheduleId)){
            logger.warn("In queryScheduleInfoByScheduleId scheduleId is null");
            return null;
        }
        return iScheduleInfoDao.queryDataByScheduleId(scheduleId);
    }

    public List<ScheduleInfo> queryScheduleInfoByHandlerUserId(String handlerUserId) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(handlerUserId)){
            logger.warn("In queryScheduleInfoByHandlerUserId handlerUserId is null");
            return null;
        }
        return iScheduleInfoDao.queryDataByHandlerUserId(handlerUserId);
    }

    public List<ScheduleInfo> queryScheduleInfoByRepairId(String repairId) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(repairId)){
            logger.warn("In queryScheduleInfoByRepairId repairId is null");
            return null;
        }
        return iScheduleInfoDao.queryDataByRepairId(repairId);
    }
}
