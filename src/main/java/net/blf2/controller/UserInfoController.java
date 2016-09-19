package net.blf2.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.blf2.model.entity.UserInfo;
import net.blf2.model.service.impl.UserServiceImpl;
import net.blf2.model.service.interfaces.IUserService;
import net.blf2.util.EnumInfo;
import net.blf2.util.GlobalStaticTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;
import javax.xml.bind.SchemaOutputResolver;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_mengxh on 2016/8/6.
 * 主要是用户的操作
 * 必须都有请求前缀 例如/User/CheckNumber?userNumber=13110572081
 */
@Controller
@ResponseBody
@RequestMapping(value = "/User",produces = "text/plain;charset=UTF-8")//请求前缀
public class UserInfoController {
    @Autowired
    @Qualifier("UserServiceImpl")
    private IUserService userService;

    private static Gson gson = new Gson();
    /**
     * 本方法用来验证学号是否为合法学号（TODO：爬虫）
     * 返回值为Allow或HasExisted，分别代表允许和已存在
     */
    @RequestMapping(value = "/checkNumber",method = RequestMethod.GET)//请求url（需加上前缀才能用）,请求方式为get
    public String isLegalNumber(@RequestParam(name = "userNumber",required = true) String userNumber){//学生学号，参数必须有，否则会404
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(!userService.isRepetitiveOfUserNumber(userNumber)) {
            responceMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
            responceMap.put(EnumInfo.MESSAGE, EnumInfo.ALLOW);
        }
        else {
            responceMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE, EnumInfo.HAS_EXISTED);
        }
        return gson.toJson(responceMap);
    }

    /**
     *本方法检查用户昵称是否存在
     * @param nickName 代表用户昵称，必须有的参数
     * @return Allow或HasExisted
     */
    @RequestMapping(value = "/checkNickName",method = RequestMethod.GET)
    public String checkNickName(@RequestParam(name = "nickName",required = true) String nickName){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(!userService.isRepetitiveOfUserNickName(nickName)){
            responceMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.ALLOW);
        }else{
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_EXISTED);
        }
        return gson.toJson(responceMap);
    }

    /**
     * 本方法检查手机号是否存在
     * @param phoneNumber 参数为手机号，必须有
     * @return Allow或HasExisted
     */
    @RequestMapping(value = "/checkPhoneNumber",method = RequestMethod.GET)
    public String checkPhoneNumber(@RequestParam(name="phoneNumber",required = true) String phoneNumber){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(!userService.isRepetitiveOfUserPhoneNumber(phoneNumber)){
            responceMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.ALLOW);
        }else{
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_EXISTED);
        }
        return gson.toJson(responceMap);
    }

    /**
     * 本方法检查用户Email是否存在
     * @param userEmail 用户Email 必须给出参数
     * @return Allow或HasExisted
     */
    @RequestMapping(value = "/checkUserEmail",method = RequestMethod.GET)
    public String checkUserEmail(@RequestParam(name = "userEmail",required = true) String userEmail){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(!userService.isRepetitiveOfUserEmail(userEmail)){
            responceMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.ALLOW);
        }else{
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_EXISTED);
        }
        return gson.toJson(responceMap);
    }

    /**
     * 本方法为测试用
     * @param show
     * @return
     */
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(@RequestParam(name = "show",required = true) String show){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        responceMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS+"hello test is successed and param is" + show);
        return gson.toJson(responceMap);
    }

    /**
     *
     * @param userEmail 用户Email
     * @param userPassword 用户密码
     * @param httpSession 这个你不用考虑
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String checkLoginInfo(@RequestParam(name="userEmail",required = true)String userEmail,
                                 @RequestParam(name = "userPassword",required = true)String userPassword,
                                 HttpSession httpSession){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        UserInfo userInfo = userService.checkLoginInfo(userEmail,userPassword);
        if(userInfo != null && (userInfo.getUserRule() & 1) == 1) {
            responceMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
            responceMap.put(EnumInfo.MESSAGE,userInfo);//登录成功
            httpSession.setAttribute("loginInfo",userInfo);
        }else if(userInfo == null){
            responceMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.LOGIN_ID_OR_PASSWORD_IS_WRONG);//登录失败
        }else if((userInfo.getUserRule() & 1) == 0){
            responceMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.FREEZE_STATUS);//账号冻结
        }
        return gson.toJson(responceMap);
    }
    private Boolean checkHasLogined(HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo != null)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    /**
     * 本方法用来注册
     * @param userNumber 用户学号
     * @param userPassword 用户密码 必须
     * @param userNickName 用户昵称 必须
     * @param userSex //用户性别
     * @param userPhoneNumber 用户手机号 必须
     * @param userEmail 用户邮箱 必须
     * @param userUniversity 用户大学 必须
     * @param userCollege 用户学院
     * @param userMajor 用户专业
     * @param userGrade 用户年级
     * @param userRule 用户权限 必须
     * @return
     */
    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    public String addUser(String userNumber, @RequestParam(name = "userPassword",required = true)String userPassword, @RequestParam(name = "userNickName",required = true)String userNickName,
                          Integer userSex, @RequestParam(name = "userPhoneNumber",required = true)String userPhoneNumber, @RequestParam(name = "userEmail",required = true)String userEmail,
                          @RequestParam(name="userUniversity",required = true)String userUniversity,
                          String userCollege, String userMajor, Integer userGrade, @RequestParam(name = "userRule",required = true)Integer userRule,
                          HttpSession httpSession){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(!(userRule == 1 || userRule == 2 || userRule == 3)){//分别对应学生，维修人员，教师
            responceMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.ILLEGAL_REGISTRATION);
            return gson.toJson(responceMap);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserCollege(userCollege);
        userInfo.setUserEmail(userEmail);
        userInfo.setUserGrade(userGrade);
        userInfo.setUserMajor(userMajor);
        userInfo.setUserNickName(userNickName);
        userInfo.setUserNumber(userNumber);
        userInfo.setUserPassword(userPassword);
        userInfo.setUserPhoneNumber(userPhoneNumber);
        userInfo.setUserSex(userSex);
        userInfo.setUserUniversity(userUniversity);
        if(userRule == 1){
            userRule = 1;
            userRule |= 1 << 6;
            userRule |=  1 << 9;
            userRule |= 1 << 13;
        }else if(userRule == 2){
            //维修员待审核
            userRule = 1 << 5;
            userRule |=  1 << 6;
            userRule |= 1 << 10;
            userRule |= 1 << 13;
        }else{
            userRule |= 1 << 6;
            userRule |=  1 << 9;
            userRule |= 1 << 13;
            userRule = 1 << 17 ;//教师待审核
        }
        userInfo.setUserRule(userRule);
        UserInfo reUserInfo = userService.registerUserInfo(userInfo);
        if(reUserInfo != null){
            httpSession.setAttribute("loginInfo",reUserInfo);
            if(userRule == (1 << 5) || userRule == (1 << 17)) {//110
                responceMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
                responceMap.put(EnumInfo.MESSAGE, EnumInfo.WAIT_FOR_REVIEWING);
            }else {
                responceMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
                responceMap.put(EnumInfo.MESSAGE, userInfo.getUserId());
            }
        }else{
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        }
        return gson.toJson(responceMap);
    }
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public String updateUser(UserInfo userInfo,HttpSession httpSession){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(!this.checkHasLogined(httpSession)){
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responceMap);
        }
        UserInfo loginedUserInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(loginedUserInfo != null && (loginedUserInfo.getUserId().equals(userInfo.getUserId()) || (loginedUserInfo.getUserRule() >> 2 & 1) == 1)) {
            if (userService.updateuserInfo(userInfo)) {
                responceMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
                responceMap.put(EnumInfo.MESSAGE, userInfo.getUserId());
            } else {
                responceMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
                responceMap.put(EnumInfo.MESSAGE, EnumInfo.DB_RETURN_VALUE_ERROR);
            }
        }else{
            responceMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE, EnumInfo.NO_JURISDICTION);
        }
        return gson.toJson(responceMap);
    }
    @RequestMapping(value = "/findUser",method = RequestMethod.GET)
    public String findUser(@RequestParam(name = "userId",required = true)String userId,HttpSession httpSession){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(!this.checkHasLogined(httpSession)){
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responceMap);
        }
        UserInfo loginedUserInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(loginedUserInfo != null && (loginedUserInfo.getUserId().equals(userId) || (loginedUserInfo.getUserRule() >> 4 & 1) ==1)){
            UserInfo userInfo = userService.queryUserInfoByUserId(userId);
            responceMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
            responceMap.put(EnumInfo.MESSAGE,userInfo);
        }else{
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
        }
        return gson.toJson(responceMap);
    }

    @RequestMapping(value = "/findAllUser",method = RequestMethod.POST)
    public String findAllUser(@RequestParam(name = "userId",required = true)String userId,HttpSession httpSession){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(!this.checkHasLogined(httpSession)){
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responceMap);
        }
        UserInfo loginedUserInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(loginedUserInfo != null && ((loginedUserInfo.getUserRule() >> 2 & 1) == 1)){
            List<UserInfo> userInfos = userService.queryUserInfoAll();
            responceMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
            responceMap.put(EnumInfo.MESSAGE,userInfos);
        }else{
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
        }
        return gson.toJson(responceMap);
    }
    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public String deleteUser(@RequestParam(name = "loginedUserId",required = true)String loginedUserId,
                             @RequestParam(name = "deleteUserId",required = true)String deleteUserId,
                             HttpSession httpSession){
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(!this.checkHasLogined(httpSession)){
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responceMap);
        }
        UserInfo loginedUserInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(loginedUserInfo != null && (loginedUserInfo.getUserRule() >> 3 & 1) == 1){
           UserInfo deleteUserInfo = userService.queryUserInfoByUserId(deleteUserId);
            if(deleteUserInfo == null) {
                responceMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
                responceMap.put(EnumInfo.MESSAGE, EnumInfo.NOT_FIND_INFO);
            }else{
                if(userService.deleteUserInfoByUserId(deleteUserId)){
                    responceMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
                    responceMap.put(EnumInfo.MESSAGE, EnumInfo.SUCCESS_INFO);
                }else{
                    responceMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
                    responceMap.put(EnumInfo.MESSAGE, EnumInfo.DB_RETURN_VALUE_ERROR);
                }
            }
        }else{
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
        }
        return gson.toJson(responceMap);
    }

    @RequestMapping(value = "/checkLogined",method = RequestMethod.POST)
    public String checkLogined(@RequestParam(name = "userId",required = true)String userId,HttpSession httpSession){
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        Map<String,Object>responceMap = new HashMap<String, Object>();
        if(userInfo == null){
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responceMap);
        }
        if(userInfo.getUserId().equals(userId)){
            responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responceMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
            return gson.toJson(responceMap);
        }
        responceMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responceMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
        return gson.toJson(responceMap);
    }
}
