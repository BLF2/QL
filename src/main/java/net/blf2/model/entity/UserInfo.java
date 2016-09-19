package net.blf2.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Entity
@Table(name="UserInfo")
public class UserInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String userId;//用户注册流水号
    @Column(name="userNumber",unique = true)
    private String userNumber;//用户登录名
    @Column(name = "userPassword")
    private String userPassword;//用户登录密码
    @Column(name = "userNickName",unique = true)
    private String userNickName;//用户昵称
    @Column(name = "userSex")
    private Integer userSex;//用户性别
    @Column(name="userPhoneNumber",unique = true)
    private String userPhoneNumber;//用户手机号
    @Column(name="userEmail")
    private String userEmail;//用户邮箱
    @Column(name = "userUniversity")
    private String userUniversity;//用户所在大学
    @Column(name = "userCollege")
    private String userCollege;//用户所在学院
    @Column(name = "userMajor")
    private String userMajor;//用户专业
    @Column(name="userGrade")
    private Integer userGrade;//用户年级
    @Column(name="userRule")
    private Integer userRule;//用户角色

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUniversity() {
        return userUniversity;
    }

    public void setUserUniversity(String userUniversity) {
        this.userUniversity = userUniversity;
    }

    public String getUserCollege() {
        return userCollege;
    }

    public void setUserCollege(String userCollege) {
        this.userCollege = userCollege;
    }

    public String getUserMajor() {
        return userMajor;
    }

    public void setUserMajor(String userMajor) {
        this.userMajor = userMajor;
    }

    public Integer getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(Integer userGrade) {
        this.userGrade = userGrade;
    }

    public Integer getUserRule() {
        return userRule;
    }

    public void setUserRule(Integer userRule) {
        this.userRule = userRule;
    }

    @Override
    public String toString(){
        return this.getClass().getName() + ":\nuserId:"+this.getUserId()+"\nuserNumber:"+this.getUserNumber() +
                "\nuserNickName:" + this.getUserNickName() + "\nuserEmail:" + this.getUserEmail() +
                "\nuserPhoneNumber:" + this.getUserPhoneNumber() + "\n";
    }
}
