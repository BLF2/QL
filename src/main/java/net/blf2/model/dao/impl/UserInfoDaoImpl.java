package net.blf2.model.dao.impl;

import net.blf2.model.dao.interfaces.IUserInfoDao;
import net.blf2.model.entity.UserInfo;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Repository("UserInfoDaoImpl")
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements IUserInfoDao{

    private Logger logger = Logger.getLogger(UserInfoDaoImpl.class);

    public List<UserInfo> queryDataAll() {
        String hql = "from UserInfo userInfo";
        Query query = null;
        try {
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql is" + hql);
            e.printStackTrace();
            return null;
        }
        return (List<UserInfo>)(query.list().size() > 0 ? query.list() : null);
    }

    public UserInfo queryDataByUserNumber(String userNumber) {
        String hql = "from UserInfo userInfo where userInfo.userId = '" + userNumber + "'";
        Query query = null;
        try {
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql is" + hql);
            e.printStackTrace();
            return null;
        }
        return (UserInfo)(query.list().size() > 0 ? query.list().get(0) : null);
    }

    public UserInfo queryDataByUserEmail(String userEmail) {
        String hql = "from UserInfo userInfo where userInfo.userEmail = '" + userEmail + "'";
        Query query = null;
        try {
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql is" + hql);
            e.printStackTrace();
            return null;
        }
        return (UserInfo)(query.list().size() > 0 ? query.list().get(0) : null);
    }

    public UserInfo queryDataByUserPhoneNumber(String userPhoneNumber) {
        String hql = "from UserInfo userInfo where userInfo.userPhoneNumber = '" + userPhoneNumber + "'";
        Query query = null;
        try {
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql is" + hql);
            e.printStackTrace();
            return null;
        }
        return (UserInfo)(query.list().size() > 0 ? query.list().get(0) : null);
    }

    public UserInfo queryDataByUserNickName(String userNickName) {
        String hql = "from UserInfo userInfo where userInfo.userNickName = '" + userNickName + "'";
        Query query = null;
        try {
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql is" + hql);
            e.printStackTrace();
            return null;
        }
        return (UserInfo)(query.list().size() > 0 ? query.list().get(0) : null);
    }

    public UserInfo queryDataByUserId(String userId) {
        String hql = "from UserInfo userInfo where userInfo.userId = '" + userId + "'";
        Query query = null;
        try {
            query = super.getSessionFactory().getCurrentSession().createQuery(hql);
        }catch (HibernateException e){
            logger.error("fail to query and hql is" + hql);
            e.printStackTrace();
            return null;
        }
        return (UserInfo)(query.list().size() > 0 ? query.list().get(0) : null);
    }
}
