package net.blf2.model.dao.impl;

import net.blf2.model.dao.interfaces.IClassificationInfoDao;
import net.blf2.model.entity.ClassificationInfo;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Repository("ClassificationInfoDaoImpl")
public class ClassificationInfoDaoImpl extends BaseDaoImpl<ClassificationInfo> implements IClassificationInfoDao {

    private static Logger logger = Logger.getLogger(ClassificationInfoDaoImpl.class);

    public ClassificationInfo queryDataByClassificationId(String classificationId) {
        String hql = "from ClassificationInfo classificationInfo where classificationInfo.classificationId = '" + classificationId + "'";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return null;
        }
        return (ClassificationInfo)(query.list().size() > 0 ? query.list().get(0) : null);
    }

    public ClassificationInfo queryDataByClassificationName(String classificationName) {
        String hql = "from ClassificationInfo classificationInfo where classificationInfo.classficationName = '" + classificationName + "'";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return null;
        }
        return (ClassificationInfo)(query.list().size() > 0 ? query.list().get(0) : null);
    }

    public List<ClassificationInfo> queryDataAll() {
        String hql = "from ClassificationInfo";
        Query query = null;
        try{
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql = " + hql);
            e.printStackTrace();
            return null;
        }
        return (List<ClassificationInfo>)(query.list().size() > 0 ? query.list() : null);
    }
}
