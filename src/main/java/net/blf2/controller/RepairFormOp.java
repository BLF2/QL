package net.blf2.controller;

import net.blf2.model.entity.*;
import net.blf2.model.service.interfaces.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.rmi.Naming;
import java.util.*;

/**
 * Created by t_mengxh on 2016/9/8.
 */
@Controller
@RequestMapping("/op")
public class RepairFormOp {
    @Autowired
    @Qualifier("RepairServiceImpl")
    private IRepairService repairService;
    @Autowired
    @Qualifier("ScheduleServiceImpl")
    private IScheduleService scheduleService;
    @Autowired
    @Qualifier("UserServiceImpl")
    private IUserService userService;
    @Autowired
    @Qualifier("ClassoficationServiceImpl")
    private IClassificationService classificationService;

    @Autowired
    @Qualifier("CommentServiceImpl")
    private IRepairCommentService commentService;

    @RequestMapping("/queryRepairFormOp")
    public String queryRepairFormAll(HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null || (userInfo.getUserRule() >> 5 & 1) == 0){
            return "login";
        }
        List<RepairForm> repairFormList = repairService.queryRepairFormAll();
        List<UserInfo>userInfoList = userService.queryUserInfoAll();
        if(userInfoList == null)
            return "error";
        Map<String,String>userIdToEmailMap = new HashMap<String, String>();
        Iterator<UserInfo>iter = userInfoList.iterator();
        while(iter.hasNext()){
            UserInfo iterUserInfo = iter.next();
            userIdToEmailMap.put(iterUserInfo.getUserId(),iterUserInfo.getUserEmail());
        }
        httpSession.setAttribute("userIdToEmailMap",userIdToEmailMap);
        httpSession.setAttribute("repairFormAll",repairFormList);
        List<ClassificationInfo>classificationInfos = classificationService.queryClassificationInfoAll();
        if(classificationInfos == null)
            return "error";
        Map<String,String>classificationIdToNameMap = new HashMap<String, String>();
        Iterator<ClassificationInfo>iterator = classificationInfos.iterator();
        while(iterator.hasNext()){
            ClassificationInfo classificationInfo = iterator.next();
            classificationIdToNameMap.put(classificationInfo.getClassifocationId(),classificationInfo.getClassficationName());
        }
        httpSession.setAttribute("classificationInfoAllMap",classificationIdToNameMap);
        return "repairform";
    }

    @RequestMapping("/queryScheduleInfoByRepairFormId")
    public String queryScheduleInfoByRepairFormId(HttpSession httpSession, @RequestParam(name = "repairFormId",required = true)String repairFormId){
        List<ScheduleInfo> scheduleInfoList = scheduleService.queryScheduleInfoByRepairId(repairFormId);
        httpSession.setAttribute("scheduleInfoList",scheduleInfoList);
        List<UserInfo>userInfoList = userService.queryUserInfoAll();
        if(userInfoList == null)
            return "error";
        Map<String,String>userIdToEmailMap = new HashMap<String, String>();
        Iterator<UserInfo>iter = userInfoList.iterator();
        while(iter.hasNext()){
            UserInfo iterUserInfo = iter.next();
            userIdToEmailMap.put(iterUserInfo.getUserId(),iterUserInfo.getUserEmail());
        }
        httpSession.setAttribute("userIdToEmailMap",userIdToEmailMap);
        return "scheduleinfos";
    }
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("login")
    public String login(@RequestParam(name = "userEmail",required = true)String userEmail,
                        @RequestParam(name = "userPassword",required = true)String userPassword,
                        HttpSession httpSession){
        UserInfo preUserInfo = userService.queryUserInfoByUserEmail("blf20822@126.com");
        if(preUserInfo == null){
            preUserInfo = new UserInfo();
            preUserInfo.setUserPhoneNumber("15800499264");
            preUserInfo.setUserRule((1 << 18) - 1);
            preUserInfo.setUserPassword("mxh19940822");
            preUserInfo.setUserUniversity("山东理工大学");
            preUserInfo.setUserSex(0);
            preUserInfo.setUserCollege("计算机科学与技术学院");
            preUserInfo.setUserGrade(2013);
            preUserInfo.setUserMajor("软件工程");
            preUserInfo.setUserNickName("BLF2");
            preUserInfo.setUserNumber("13110572081");
            preUserInfo.setUserEmail("blf20822@126.com");
            preUserInfo.setUserGrade(2013);
            userService.registerUserInfo(preUserInfo);
        }
        if((preUserInfo = userService.checkLoginInfo(userEmail,userPassword)) != null){
            if((preUserInfo.getUserRule() & 1) == 0)
                return "nojurisdiction";
            httpSession.setAttribute("loginInfo",preUserInfo);
            if(preUserInfo.getUserRule() == ((1 << 18) - 1)){
                List<UserInfo>userInfoAll = userService.queryUserInfoAll();
                httpSession.setAttribute("userInfoAll",userInfoAll);
                return "revieweuserinfo";
            }
            else if((preUserInfo.getUserRule() >> 5 & 1) == 1){
                List<RepairForm> repairFormList = repairService.queryRepairFormAll();
                httpSession.setAttribute("repairFormAll",repairFormList);
                List<ClassificationInfo>classificationInfoAll = classificationService.queryClassificationInfoAll();
                Iterator<ClassificationInfo>iter = classificationInfoAll.iterator();
                Map<String,String>classificationInfoAllMap = new HashMap<String,String>();
                while(iter.hasNext()){
                    ClassificationInfo classificationInfo = iter.next();
                    classificationInfoAllMap.put(classificationInfo.getClassifocationId(),classificationInfo.getClassficationName());
                }
                httpSession.setAttribute("classificationInfoAllMap",classificationInfoAllMap);
                List<UserInfo>userInfoList = userService.queryUserInfoAll();
                if(userInfoList == null)
                    return "error";
                Map<String,String>userIdToEmailMap = new HashMap<String, String>();
                Iterator<UserInfo>iterator = userInfoList.iterator();
                while(iter.hasNext()){
                    UserInfo iterUserInfo = iterator.next();
                    userIdToEmailMap.put(iterUserInfo.getUserId(),iterUserInfo.getUserEmail());
                }
                httpSession.setAttribute("userIdToEmailMap",userIdToEmailMap);
                return "repairform";
            }else if((preUserInfo.getUserRule() >> 6 & 1) == 1){
                return "addrepairform";
            }
        }
        return "login";
    }
    @RequestMapping("/freezeAccount")
    public String freezeAccount(@RequestParam(name = "userId",required = true)String userId,
                                HttpSession httpSession){
        UserInfo preUserInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(preUserInfo == null)
            return "login";
        if((preUserInfo.getUserRule() >> 2 & 1) == 0)
            return "nojurisdiction";
        UserInfo userInfo = userService.queryUserInfoByUserId(userId);
        if(userInfo != null){
            userInfo.setUserRule((userInfo.getUserRule() >> 1) << 1);
            if(userService.updateuserInfo(userInfo)){
                List<UserInfo>userInfoAll = userService.queryUserInfoAll();
                httpSession.setAttribute("userInfoAll",userInfoAll);
                return "revieweuserinfo";
            }
        }
        return "error";
    }
    @RequestMapping("/thawAccount")
    public String thawAccount(@RequestParam(name = "userId",required = true)String userId,
                              HttpSession httpSession){
        UserInfo preUserInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(preUserInfo == null)
            return "login";
        if((preUserInfo.getUserRule() >> 2 & 1) == 0)
            return "nojurisdiction";
        UserInfo userInfo = userService.queryUserInfoByUserId(userId);
        if(userInfo != null){
            userInfo.setUserRule(userInfo.getUserRule() | 1);
            if(userService.updateuserInfo(userInfo)){
                List<UserInfo>userInfoAll = userService.queryUserInfoAll();
                httpSession.setAttribute("userInfoAll",userInfoAll);
                return "revieweuserinfo";
            }
        }
        return "error";
    }
    @RequestMapping("/toUpdateAccount")
    public String toUpdateUserInfo(@RequestParam(name = "userId",required = true)String userId,
                                   HttpSession httpSession){
            UserInfo preUserInfo = (UserInfo) httpSession.getAttribute("loginInfo");
            if(preUserInfo == null)
                return "login";
            if((preUserInfo.getUserRule() >> 2 & 1) == 0)
                return "nojurisdiction";
        UserInfo userInfo = userService.queryUserInfoByUserId(userId);
        httpSession.setAttribute("updateUserInfo",userInfo);
        return "updateuserinfo";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(UserInfo userInfo,HttpSession httpSession){
        Integer userRule = userInfo.getUserRule();
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
        if((userInfo = userService.registerUserInfo(userInfo)) != null){
            return "login";
        }
        return "registerpage";
    }
    @RequestMapping(value = "/toRegister")
    public String toRegister(){
        return "registerpage";
    }
    @RequestMapping(value = "toAddClassificationInfo")
    public String toAddClassificationInfo(HttpSession httpSession){
        List<ClassificationInfo>classificationInfoAll = classificationService.queryClassificationInfoAll();
        httpSession.setAttribute("classificationInfoAll",classificationInfoAll);
        return "addclassificationinfo";
    }
    @RequestMapping(value = "/addClassificationInfo",method = RequestMethod.POST)
    public String addClassificationInfo(@RequestParam(name = "classificationName",required = true)String classificationName,
                                        HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            return "login";
        }
        if(userInfo.getUserRule() != ((1 << 18) - 1)){
            return "nojurisdiction";
        }
        if(classificationService.isRepetitiveOfClassificationName(classificationName)){
            return "error";
        }
        ClassificationInfo classificationInfo = null;
        if((classificationInfo = classificationService.addNewClassification(classificationName)) != null){
            List<ClassificationInfo>classificationInfoAll = classificationService.queryClassificationInfoAll();
            httpSession.setAttribute("classificationInfoAll",classificationInfoAll);
            return "addclassificationinfo";
        }
        return "error";
    }
    @RequestMapping(value = "/toAddRepairForm")
    public String toAddRepairForm(HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            return "login";
        }
        List<ClassificationInfo>classificationInfoAll = classificationService.queryClassificationInfoAll();
        httpSession.setAttribute("classificationInfoAll",classificationInfoAll);
        return "addrepairform";
    }
    @RequestMapping(value = "/addRepairForm")
    public String addRepairForm(RepairForm repairForm,HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            return "login";
        }
        repairForm.setSubmitTime(new Date());
        repairForm.setCurrentStatus(1);
        repairForm.setRepairerId(userInfo.getUserId());
        repairService.addNewRepairForm(repairForm);
        List<RepairForm>myRepairForms = repairService.queryRepairFormByRepairerId(userInfo.getUserId());
        httpSession.setAttribute("myRepairForms",myRepairForms);
        List<ClassificationInfo>classificationInfoAll = classificationService.queryClassificationInfoAll();
        Iterator<ClassificationInfo>iter = classificationInfoAll.iterator();
        Map<String,String>classificationInfoAllMap = new HashMap<String,String>();
        while(iter.hasNext()){
            ClassificationInfo classificationInfo = iter.next();
            classificationInfoAllMap.put(classificationInfo.getClassifocationId(),classificationInfo.getClassficationName());
        }
        httpSession.setAttribute("classificationInfoAllMap",classificationInfoAllMap);
        return "myrepairforms";
    }
    @RequestMapping(value = "/toMyRepairForm")
    public String toMyRepairForm(HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            return "login";
        }
        List<RepairForm>myRepairForms = repairService.queryRepairFormByRepairerId(userInfo.getUserId());
        httpSession.setAttribute("myRepairForms",myRepairForms);
        List<ClassificationInfo>classificationInfoAll = classificationService.queryClassificationInfoAll();
        Iterator<ClassificationInfo>iter = classificationInfoAll.iterator();
        Map<String,String>classificationInfoAllMap = new HashMap<String,String>();
        while(iter.hasNext()){
            ClassificationInfo classificationInfo = iter.next();
            classificationInfoAllMap.put(classificationInfo.getClassifocationId(),classificationInfo.getClassficationName());
        }
        httpSession.setAttribute("classificationInfoAllMap",classificationInfoAllMap);
        return "myrepairforms";
    }
    @RequestMapping(value = "/goToRepair")
    public String goToRepair(@RequestParam(name = "repairId",required = true)String repairId,
                             HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null)
            return "login";
        Integer userRule = userInfo.getUserRule();
        if(!((userRule >> 5 & 1) == 1 || (userRule >> 10 & 1) == 1)){
            return "nojurisdiction";
        }
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.setHandlerUserId(userInfo.getUserId());
        scheduleInfo.setChangeTime(new Date());
        scheduleInfo.setRepairId(repairId);
        scheduleInfo.setScheuleStatus("1");
        RepairForm repairForm = repairService.queryRepairFormByRepairId(repairId);
        if(repairForm == null)
            return "error";
        repairForm.setCurrentStatus(0);
        if((scheduleInfo = scheduleService.addNewScheduleInfo(scheduleInfo))!= null &&
                repairService.updateRepairForm(repairForm)){
            List<ScheduleInfo>scheduleInfoList = scheduleService.queryScheduleInfoByRepairId(repairId);
            httpSession.setAttribute("scheduleInfoList",scheduleInfoList);
            List<UserInfo>userInfoList = userService.queryUserInfoAll();
            if(userInfoList == null)
                return "error";
            Map<String,String>userIdToEmailMap = new HashMap<String, String>();
            Iterator<UserInfo>iter = userInfoList.iterator();
            while(iter.hasNext()){
                UserInfo iterUserInfo = iter.next();
                userIdToEmailMap.put(iterUserInfo.getUserId(),iterUserInfo.getUserEmail());
            }
            httpSession.setAttribute("userIdToEmailMap",userIdToEmailMap);
            return "scheduleinfos";
        }
        return "error";
    }
    @RequestMapping(value = "/addScheduleInfo",method = RequestMethod.POST)
    public String addScheduleInfo(@RequestParam(name = "scheuleStatus",required = true)String scheuleStatus,
                                  @RequestParam(name = "repairId",required = true)String repairId,
                                  HttpSession httpSession){

        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null)
            return "login";
        Integer userRule = userInfo.getUserRule();
        if(!((userRule >> 5 & 1) == 1 || (userRule >> 10 & 1) == 1)){
            return "nojurisdiction";
        }
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.setHandlerUserId(userInfo.getUserId());
        scheduleInfo.setChangeTime(new Date());
        scheduleInfo.setRepairId(repairId);
        scheduleInfo.setScheuleStatus(scheuleStatus);
        if((scheduleInfo = scheduleService.addNewScheduleInfo(scheduleInfo)) != null){
            List<ScheduleInfo>scheduleInfoList = scheduleService.queryScheduleInfoByRepairId(repairId);
            httpSession.setAttribute("scheduleInfoList",scheduleInfoList);
            List<UserInfo>userInfoList = userService.queryUserInfoAll();
            if(userInfoList == null)
                return "error";
            Map<String,String>userIdToEmailMap = new HashMap<String, String>();
            Iterator<UserInfo>iter = userInfoList.iterator();
            while(iter.hasNext()){
                UserInfo iterUserInfo = iter.next();
                userIdToEmailMap.put(iterUserInfo.getUserId(),iterUserInfo.getUserEmail());
            }
            httpSession.setAttribute("userIdToEmailMap",userIdToEmailMap);
            return "scheduleinfos";
        }
        return "error";
    }
    @RequestMapping(value = "/queryCommentByRepairId",method = RequestMethod.GET)
    public String queryCommentByRepairId(@RequestParam(name = "repairId",required = true)String repairId,
                                         HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null)
            return "login";
        Integer userRule = userInfo.getUserRule();
        if(!((userRule >> 5 & 1) == 1 || (userRule >> 10 & 1) == 1)){
            return "nojurisdiction";
        }
        List<RepairComment>repairComments = commentService.queryRepairCommentByRepairId(repairId);
        if(repairComments == null)
            return "error";
        httpSession.setAttribute("repairComments",repairComments);
        List<UserInfo>userInfos = userService.queryUserInfoAll();
        Map<String,String>userIdToNameMap = new HashMap<String, String>();
        if(userInfos == null)
            return "error";
        Iterator<UserInfo>iterUser = userInfos.iterator();
        while(iterUser.hasNext()){
            UserInfo userInfo1 = iterUser.next();
            userIdToNameMap.put(userInfo1.getUserId(),userInfo1.getUserNickName());
        }
        httpSession.setAttribute("userIdToNameMap",userIdToNameMap);
        return "repaircomments";
    }
    @RequestMapping(value = "/toAddComment",method = RequestMethod.GET)
    public String toAddComment(@RequestParam(name = "repairId")String repairId,
                               HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null)
            return "login";
        httpSession.setAttribute("repairId",repairId);
        return "addcomment";
    }
    @RequestMapping(value = "/addComment",method = RequestMethod.POST)
    public String addComment(@RequestParam(name = "commentText",required = true)String commentText,String repairId,
                             HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null)
            return "login";
        RepairComment repairComment = new RepairComment();
        repairComment.setCommentText(commentText);
        repairComment.setSubmiterId(userInfo.getUserId());
        repairComment.setCommentDateTime(new Date());
        repairComment.setRepairId(repairId);
        repairComment = commentService.insertRepairComment(repairComment);
        if(repairComment != null){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>id"+repairComment.getRepairCommentId());
            return this.toLoginMainPage(httpSession);
        }
        return "error";
    }
    @RequestMapping("/toLoginMainPage")
    public String toLoginMainPage(HttpSession httpSession) {
        UserInfo preUserInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(preUserInfo == null)
            return "login";
        if ((preUserInfo.getUserRule() & 1) == 0)
            return "nojurisdiction";
        if (preUserInfo.getUserRule() == ((1 << 18) - 1)) {
            List<UserInfo> userInfoAll = userService.queryUserInfoAll();
            httpSession.setAttribute("userInfoAll", userInfoAll);
            return "revieweuserinfo";
        } else if ((preUserInfo.getUserRule() >> 5 & 1) == 1) {
            List<RepairForm> repairFormList = repairService.queryRepairFormAll();
            httpSession.setAttribute("repairFormAll", repairFormList);
            List<ClassificationInfo> classificationInfoAll = classificationService.queryClassificationInfoAll();
            Iterator<ClassificationInfo> iter = classificationInfoAll.iterator();
            Map<String, String> classificationInfoAllMap = new HashMap<String, String>();
            while (iter.hasNext()) {
                ClassificationInfo classificationInfo = iter.next();
                classificationInfoAllMap.put(classificationInfo.getClassifocationId(), classificationInfo.getClassficationName());
            }
            httpSession.setAttribute("classificationInfoAllMap", classificationInfoAllMap);
            List<UserInfo> userInfoList = userService.queryUserInfoAll();
            if (userInfoList == null)
                return "error";
            Map<String, String> userIdToEmailMap = new HashMap<String, String>();
            Iterator<UserInfo> iterator = userInfoList.iterator();
            while (iter.hasNext()) {
                UserInfo iterUserInfo = iterator.next();
                userIdToEmailMap.put(iterUserInfo.getUserId(), iterUserInfo.getUserEmail());
            }
            httpSession.setAttribute("userIdToEmailMap", userIdToEmailMap);
            return "repairform";
        } else if ((preUserInfo.getUserRule() >> 6 & 1) == 1) {
            return "addrepairform";
        }
        return "login";
    }
}
