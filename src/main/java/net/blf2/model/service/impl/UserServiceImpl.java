package net.blf2.model.service.impl;

import net.blf2.model.dao.interfaces.IClassificationInfoDao;
import net.blf2.model.dao.interfaces.IRepairFormDao;
import net.blf2.model.dao.interfaces.IScheduleInfoDao;
import net.blf2.model.dao.interfaces.IUserInfoDao;
import net.blf2.model.entity.RepairForm;
import net.blf2.model.entity.ScheduleInfo;
import net.blf2.model.entity.UserInfo;
import net.blf2.model.service.interfaces.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by t_mengxh on 2016/7/26.
 * 用户service
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements IUserService {
    @Autowired
    @Qualifier("UserInfoDaoImpl")
    private IUserInfoDao userInfoDao;

    @Autowired
    @Qualifier("RepairFormDaoImpl")
    private IRepairFormDao repairFormDao;

    @Autowired
    @Qualifier("ScheduleInfoDaoImpl")
    private IScheduleInfoDao scheduleInfoDao;

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    public IUserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public void setUserInfoDao(IUserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public Boolean isRepetitiveOfUserNumber(String userNumber) {
        return userInfoDao.queryDataByUserNumber(userNumber) == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public Boolean isRepetitiveOfUserNickName(String userNickName) {
        return userInfoDao.queryDataByUserNickName(userNickName) == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public Boolean isRepetitiveOfUserPhoneNumber(String userPhoneNumber) {
        return userInfoDao.queryDataByUserPhoneNumber(userPhoneNumber) == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public Boolean isRepetitiveOfUserEmail(String userEmail) {
        return userInfoDao.queryDataByUserEmail(userEmail) == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public UserInfo registerUserInfo(UserInfo userInfo) {
        if(userInfo == null) {
            logger.error("userInfo is null");
            return null;
        }
        //checkUserNumber();
        return userInfoDao.insertData(userInfo);
    }

    public Boolean deleteUserInfoByUserInfo(UserInfo userInfo) {
        return userInfo == null ? Boolean.FALSE : this.cleanUserInfoRelevantInfo(userInfo) && userInfoDao.deleteData(userInfo);
    }

    public Boolean deleteUserInfoByUserId(String userId) {
        if(userId == null || userId.isEmpty()){
            logger.warn("In deleteUserInfoByUserNumber userId = " + userId);
            return Boolean.FALSE;
        }
        UserInfo userInfo = userInfoDao.queryDataByUserId(userId);
        if(userInfo == null){
            logger.warn("In deleteUserInfoByUserNumber can't find the UserInfo of userId= " + userId);
            return Boolean.FALSE;
        }
        return this.cleanUserInfoRelevantInfo(userInfo) && this.deleteUserInfoByUserInfo(userInfo);
    }

    public Boolean deleteUserInfoByUserNumber(String userNumber) {
        if(userNumber == null || userNumber.isEmpty()){
            logger.warn("In deleteUserInfoByUserNumber userNumber = " + userNumber);
            return Boolean.FALSE;
        }
        UserInfo userInfo = userInfoDao.queryDataByUserNumber(userNumber);
        if(userInfo == null){
            logger.warn("In deleteUserInfoByUserNumber can't find the UserInfo of userNumber = " + userNumber);
            return Boolean.FALSE;
        }
        return this.cleanUserInfoRelevantInfo(userInfo) && this.deleteUserInfoByUserInfo(userInfo);
    }

    public IRepairFormDao getRepairFormDao() {
        return repairFormDao;
    }

    public void setRepairFormDao(IRepairFormDao repairFormDao) {
        this.repairFormDao = repairFormDao;
    }

    public IScheduleInfoDao getScheduleInfoDao() {
        return scheduleInfoDao;
    }

    public void setScheduleInfoDao(IScheduleInfoDao scheduleInfoDao) {
        this.scheduleInfoDao = scheduleInfoDao;
    }

    private Boolean cleanUserInfoRelevantInfo(UserInfo userInfo){
        List<RepairForm> repairFormList= repairFormDao.queryDataByRepairerId(userInfo.getUserId());
        if(repairFormList == null){
            logger.warn("In cleanUserInfoRelevantInfo repairFormList is null");
            return Boolean.FALSE;
        }
        Iterator<RepairForm> iterator = repairFormList.iterator();
        while(iterator.hasNext()){

            RepairForm repairForm = iterator.next();
            List<ScheduleInfo> scheduleInfoList = scheduleInfoDao.queryDataByRepairId(repairForm.getRepairId());
            if(scheduleInfoList == null){
                logger.warn("In cleanUserInfoRelevantInfo scheduleInfoList is null");
                continue;
            }
            Iterator<ScheduleInfo> iter = scheduleInfoList.iterator();
            while(iter.hasNext()){
                scheduleInfoDao.deleteData(iter.next());
            }
            repairFormDao.deleteData(repairForm);
        }
        return Boolean.TRUE;
    }
    public Boolean updateuserInfo(UserInfo userInfo) {
        return userInfoDao.updateData(userInfo);
    }

    public UserInfo checkLoginInfo(String userEmail, String userPassword) {
        if(userEmail == null || userEmail.isEmpty() || userPassword == null || userPassword.isEmpty()) {
            logger.warn("In checkLoginInfo userNumber or userPassword illegal");
            return null;
        }
        UserInfo userInfo = null;
        if((userInfo = userInfoDao.queryDataByUserEmail(userEmail)) != null){
            if(userInfo.getUserPassword().equals(userPassword)){
                return userInfo;
            }
        }
        return null;
    }

    public UserInfo queryUserInfoByUserId(String userId) {
        return userInfoDao.queryDataByUserId(userId);
    }

    public UserInfo queryUserInfoByUserNumber(String userNumber) {
        return userInfoDao.queryDataByUserNumber(userNumber);
    }

    public UserInfo queryUserInfoByUserEmail(String userEmail) {
        return userInfoDao.queryDataByUserEmail(userEmail);
    }

    public UserInfo queryUserInfoByUserPhoneNumber(String userPhoneNumber) {
        return userInfoDao.queryDataByUserPhoneNumber(userPhoneNumber);
    }

    public List<UserInfo> queryUserInfoAll() {
        return userInfoDao.queryDataAll();
    }
}
