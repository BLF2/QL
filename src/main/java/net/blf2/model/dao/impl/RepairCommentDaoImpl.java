package net.blf2.model.dao.impl;

import net.blf2.model.dao.interfaces.IRepairCommentDao;
import net.blf2.model.entity.RepairComment;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by blf2 on 16-9-17.
 */

@Repository("RepairCommentDaoImpl")
public class RepairCommentDaoImpl extends BaseDaoImpl<RepairComment> implements IRepairCommentDao {

    private Logger logger = Logger.getLogger(RepairFormDaoImpl.class);

    @Override
    public RepairComment queryRepairCommentById(String repairCommentId) {
        String hql = "from RepairComment repairComment where repairComment.repairCommentId=" +
                ":repairCommentId";
        Query query = null;
        try {
            query = super.getCurrentSession().createQuery(hql).setString("repairCommentId", repairCommentId);
        }catch (HibernateException e){
            logger.error("In RepairCommentDaoImpl queryRepairCommentById is error!");
            e.printStackTrace();
        }
        return query == null ? null : (RepairComment) query.list().get(0);
    }

    @Override
    public List<RepairComment> queryCommentByRepairId(String repairId) {
        String hql = "from RepairComment repairComment where repairComment.repairId=:repairId " +
                "order by repairComment.commentDateTime";
        Query query = null;
        try {
            query = super.getCurrentSession().createQuery(hql).setString("repairId",repairId);
        }catch (HibernateException e){
            logger.error("In RepairCommentDaoImpl queryCommentByRepairId is error!");
            e.printStackTrace();
        }
        return query == null ? null : (List<RepairComment>)query.list();
    }

    @Override
    public List<RepairComment> queryDataAll() {
        String hql = "from RepairComment";
        Query query = null;
        try {
            query = super.getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("In"+this.getClass().getName()+" queryDataAll is error");
            e.printStackTrace();
        }
        return query == null ? null : (List<RepairComment>)query.list();
    }
}
