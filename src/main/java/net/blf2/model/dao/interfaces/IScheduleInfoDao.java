package net.blf2.model.dao.interfaces;

import net.blf2.model.entity.ScheduleInfo;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/24.
 */
public interface IScheduleInfoDao extends IBaseDao<ScheduleInfo>{
    public ScheduleInfo queryDataByScheduleId(String scheduleId);
    public List<ScheduleInfo> queryDataByHandlerUserId(String handlerUserId);
    public List<ScheduleInfo> queryDataByRepairId(String repairId);
}
