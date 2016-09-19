package net.blf2.model.dao.impl;

import net.blf2.model.dao.interfaces.IBaseDao;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Repository("BaseDaoImpl")
public abstract class BaseDaoImpl<T> implements IBaseDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private Logger logger = Logger.getLogger(BaseDaoImpl.class);

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    public T insertData(T t) {
        try{
            this.sessionFactory.getCurrentSession().save(t);
        }catch (Exception e){
            logger.error("fail to insert into db and class information " + t.toString());
            e.printStackTrace();
            return null;
        }
        return  t;
    }

    public Boolean deleteData(T t) {
        try {
            this.sessionFactory.getCurrentSession().delete(t);
        }catch (HibernateException e){
            logger.error("fail to delete into db and class information " + t.toString());
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean updateData(T t) {
        try {
            this.sessionFactory.getCurrentSession().update(t);
        }catch (HibernateException e){
            logger.error("fail to update into db and class information " + t.toString());
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
