package net.blf2.model.service.interfaces;

import net.blf2.model.entity.UserInfo;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/25.
 */
public interface IUserService {
    public Boolean isRepetitiveOfUserNumber(String userNumber);
    public Boolean isRepetitiveOfUserNickName(String userNickName);
    public Boolean isRepetitiveOfUserPhoneNumber(String userPhoneNumber);
    public Boolean isRepetitiveOfUserEmail(String userEmail);
    public UserInfo registerUserInfo(UserInfo userInfo);
    public Boolean deleteUserInfoByUserInfo(UserInfo userInfo);
    public Boolean deleteUserInfoByUserId(String userId);
    public Boolean deleteUserInfoByUserNumber(String userNumber);
    public Boolean updateuserInfo(UserInfo userInfo);
    public UserInfo checkLoginInfo(String userEmail,String userPassword);
    public UserInfo  queryUserInfoByUserId(String userId);
    public UserInfo  queryUserInfoByUserNumber(String userNumber);
    public UserInfo  queryUserInfoByUserEmail(String userEmail);
    public UserInfo queryUserInfoByUserPhoneNumber(String userPhoneNumber);
    public List<UserInfo> queryUserInfoAll();
}
