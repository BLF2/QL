package net.blf2.model.dao.impl;

import net.blf2.model.dao.interfaces.IScheduleInfoDao;
import net.blf2.model.entity.ScheduleInfo;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Repository("ScheduleInfoDaoImpl")
public class ScheduleInfoDaoImpl extends BaseDaoImpl<ScheduleInfo> implements IScheduleInfoDao{

    private Logger logger = Logger.getLogger(ScheduleInfoDaoImpl.class);

    public ScheduleInfo queryDataByScheduleId(String scheduleId) {
        String hql = "from ScheduleInfo scheduleInfo where scheduleInfo.scheduleId = '" + scheduleId + "'";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return null;
        }
        return (ScheduleInfo)(query.list().size() > 0 ? query.list().get(0) : null);
    }

    public List<ScheduleInfo> queryDataByHandlerUserId(String handlerUserId) {
        String hql = "from ScheduleInfo scheduleInfo where scheduleInfo.handlerUserId = '" + handlerUserId + "' order by scheduleInfo.changeTime";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return null;
        }
        return (List<ScheduleInfo>)(query.list().size() > 0 ? query.list() : null);
    }

    public List<ScheduleInfo> queryDataByRepairId(String repairId) {
        String hql = "from ScheduleInfo scheduleInfo where scheduleInfo.repairId = '" + repairId + "' order by scheduleInfo.changeTime";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return null;
        }
        return (List<ScheduleInfo>)(query.list().size() > 0 ? query.list() : null);
    }

    public List<ScheduleInfo> queryDataAll() {
        String hql = "from ScheduleInfo scheduleInfo order by scheduleInfo.changeTime";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return null;
        }
        return (List<ScheduleInfo>)(query.list().size() > 0 ? query.list() : null);
    }
}
