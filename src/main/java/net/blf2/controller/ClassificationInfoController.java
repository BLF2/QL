package net.blf2.controller;

import com.google.gson.Gson;
import net.blf2.model.entity.ClassificationInfo;
import net.blf2.model.entity.UserInfo;
import net.blf2.model.service.interfaces.IClassificationService;
import net.blf2.util.EnumInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by blf2 on 16-8-28.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/ClassifictionInfo",produces = "text/plain;charset=UTF-8")
public class ClassificationInfoController {
    @Autowired
    @Qualifier("ClassoficationServiceImpl")
    private IClassificationService classificationService;
    
    private static Gson gson = new Gson();

    @RequestMapping(value = "/addClassifictionInfo",method = RequestMethod.POST)
    public String addClassifictionInfo(@RequestParam(name = "classifictionName",required = true)String classifictionName,
                                       HttpSession httpSession){
        Map<String,Object> responseMap = new HashMap<String,Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 14 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        ClassificationInfo reClassificationInfo = classificationService.addNewClassification(classifictionName);
        if(reClassificationInfo != null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/queryClassifictionInfoById",method = RequestMethod.GET)
    public String queryClassifictionInfoById(@RequestParam(name = "classifictionInfoId",required = true)String classifictionInfoId,
                                             HttpSession httpSession){
        Map<String,Object> responseMap = new HashMap<String,Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 13 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        ClassificationInfo classificationInfo = classificationService.queryClassificationInfoByClassificationId(classifictionInfoId);
        if(classificationInfo != null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,classificationInfo);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/updateClassifictionInfo",method = RequestMethod.POST)
    public String updateClassifictionInfo(ClassificationInfo classificationInfo,HttpSession httpSession){
        Map<String,Object> responseMap = new HashMap<String,Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 15 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        if(classificationInfo != null) {
            if (classificationService.updateClassificationInfo(classificationInfo.getClassifocationId(),classificationInfo.getClassficationName())) {
                responseMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
                responseMap.put(EnumInfo.MESSAGE, EnumInfo.SUCCESS_INFO);
                return gson.toJson(responseMap);
            }
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/isRepetitiveOfClassificationName",method = RequestMethod.GET)
    public String isRepetitiveOfClassificationName(@RequestParam(name = "classificationName",required = true)String classificationName,
                                                   HttpSession httpSession){
        Map<String,Object> responseMap = new HashMap<String,Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 15 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        if(classificationService.isRepetitiveOfClassificationName(classificationName)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_EXISTED);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.ALLOW);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/deleteClassifictionInfoById",method = RequestMethod.GET)
    public String deleteClassifictionInfoById(@RequestParam(name = "classificationId",required = true)String classificationId,
                                                   HttpSession httpSession){
        Map<String,Object> responseMap = new HashMap<String,Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 16 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        if(!classificationService.deleteClassificationInfoByClassificationId(classificationId)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/queryClassificationInfoAll",method = RequestMethod.GET)
    public String queryClassificationInfoAll(HttpSession httpSession){
        Map<String,Object> responseMap = new HashMap<String,Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 13 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        List<ClassificationInfo> classificationInfos = classificationService.queryClassificationInfoAll();
       if(classificationInfos == null){
           responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
           responseMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
           return gson.toJson(responseMap);
       }
        responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
        responseMap.put(EnumInfo.MESSAGE,classificationInfos);
        return gson.toJson(responseMap);
    }
}
