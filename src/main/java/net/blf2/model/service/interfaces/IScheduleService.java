package net.blf2.model.service.interfaces;

import net.blf2.model.entity.ScheduleInfo;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/30.
 */
public interface IScheduleService {
    public ScheduleInfo addNewScheduleInfo(ScheduleInfo scheduleInfo);
    public Boolean updateScheduleInfo(ScheduleInfo scheduleInfo);
    public Boolean deleteScheduleInfo(ScheduleInfo scheduleInfo);
    public ScheduleInfo queryScheduleInfoByScheduleId(String scheduleId);
    public List<ScheduleInfo>  queryScheduleInfoByHandlerUserId(String handlerUserId);
    public List<ScheduleInfo> queryScheduleInfoByRepairId(String repairId);
}
