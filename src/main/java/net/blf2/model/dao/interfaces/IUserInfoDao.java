package net.blf2.model.dao.interfaces;

import net.blf2.model.entity.UserInfo;

import java.util.List;
import java.util.UUID;

/**
 * Created by t_mengxh on 2016/7/24.
 */
public interface IUserInfoDao extends IBaseDao<UserInfo> {
    public UserInfo queryDataByUserNumber(String userNumber);
    public UserInfo queryDataByUserEmail(String userEmail);
    public UserInfo queryDataByUserPhoneNumber(String userPhoneNumber);
    public UserInfo queryDataByUserNickName(String userNickName);
    public UserInfo queryDataByUserId(String userId);
}
