package net.blf2.model.dao.impl;

import net.blf2.model.dao.interfaces.IRepairFormDao;
import net.blf2.model.entity.RepairForm;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Repository("RepairFormDaoImpl")
public class RepairFormDaoImpl extends BaseDaoImpl<RepairForm> implements IRepairFormDao {
    private Logger logger = Logger.getLogger(RepairFormDaoImpl.class);

    public RepairForm queryDataByRepairId(String repairId) {
        String hql = "from RepairForm repairForm where repairForm.repairId = '" + repairId + "'";
        Query query = null;
        try {
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return  null;
        }
        return (RepairForm)(query.list().size() > 0 ? query.list().get(0) : null);
    }

    public List<RepairForm> queryDataByRepairerId(String repairerId) {
        String hql = "from RepairForm repairForm where repairForm.repairerId = '" + repairerId + "'";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return  null;
        }
        return (List<RepairForm>)(query.list().size() > 0 ? query.list() : null);
    }

    public List<RepairForm> queryDataByClassificationId(String classificationId) {
        String hql = "from RepairForm repairForm where repairForm.classificationId = '" + classificationId + "'";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return  null;
        }
        return (List<RepairForm>)(query.list().size() > 0 ? query.list() : null);
    }

    public List<RepairForm> queryDataByDescriptionTitle(String descriptionTitle) {
        String queryStr = "%" + descriptionTitle.trim().replaceAll("\\s+","%") + "%";
        String hql = "from RepairForm repairForm where repairForm.descriptionTitle like '" + queryStr + "'";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return  null;
        }
        return (List<RepairForm>)(query.list().size() > 0 ? query.list() : null);
    }

    public List<RepairForm> queryDataByRepairPriority() {
        String hql = "from RepairForm repairForm order by repairForm.repairPriority desc";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return  null;
        }
        return (List<RepairForm>)(query.list().size() > 0 ? query.list() : null);
    }

    public List<RepairForm> queryDataByCurrentStatus(Integer currentStatus) {
        String hql = "from RepairForm repairForm where repairForm.currentStatus = " + currentStatus + " order by repairForm.submitTime desc";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return  null;
        }
        return (List<RepairForm>)(query.list().size() > 0 ? query.list() : null);
    }

    public List<RepairForm> queryDataAll() {
        String hql = "from RepairForm ";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return  null;
        }
        return (List<RepairForm>)(query.list().size() > 0 ? query.list() : null);
    }
}
