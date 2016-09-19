package net.blf2.controller;

import com.google.gson.Gson;
import net.blf2.model.entity.RepairForm;
import net.blf2.model.entity.UserInfo;
import net.blf2.model.service.impl.RepairServiceImpl;
import net.blf2.model.service.interfaces.IRepairService;
import net.blf2.util.EnumInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_mengxh on 2016/8/20.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/RepairForm",produces = "text/plain;charset=UTF-8")
public class RepairFormController {
    @Autowired
    @Qualifier("RepairServiceImpl")
    private IRepairService repairService;
    
    private static Gson gson = new Gson();

    @RequestMapping(value = "/addRepairForm",method = RequestMethod.POST)
    public String addRepairForm(@RequestParam(name = "submiterId",required = true)String submiterId,
                                @RequestParam(name = "classificationId",required = true)String classificationId,
                                @RequestParam(name = "descriptionTitle",required = true)String descriptionTitle,
                                @RequestParam(name = "descriptionText",required = true)String descriptionText,
                                @RequestParam(name = "repairPriority",required = true)Integer repairPriority,
                                String imgePath,String videoPath,String repairNote,HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        if(!checkIsLogin(httpSession)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo != null && (userInfo.getUserRule() & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.FREEZE_STATUS);
            return gson.toJson(responseMap);
        }
        Integer currentStatus = 1;
        Date submitTime = new Date();
        RepairForm repairForm = new RepairForm();
        repairForm.setRepairerId(submiterId);
        repairForm.setClassificationId(classificationId);
        repairForm.setDescriptionTitle(descriptionTitle);
        repairForm.setDescriptionText(descriptionText);
        repairForm.setImgePath(imgePath);
        repairForm.setVideoPath(videoPath);
        repairForm.setSubmitTime(submitTime);
        repairForm.setRepairPriority(repairPriority);
        repairForm.setCurrentStatus(currentStatus);
        repairForm.setRepairNote(repairNote);
        RepairForm reRepairForm = repairService.addNewRepairForm(repairForm);
        if(repairForm != null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return null;
    }
    @RequestMapping(value = "/queryMyRepairForm",method = RequestMethod.GET)
    public String queryMyRepairForm(@RequestParam(name = "userId",required = true)String userId,
                                    HttpSession httpSession){
        Map<String,Object> responseMap = new HashMap<String, Object>();
        if(!checkIsLogin(httpSession)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if((userInfo.getUserRule() & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.FREEZE_STATUS);
            return gson.toJson(responseMap);
        }
        if(((userInfo.getUserRule() >> 5) & 1 ) == 0 || !userId.equals(userInfo.getUserId())){
            responseMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE, EnumInfo.NO_JURISDICTION);
        }
        List<RepairForm>repairForms = repairService.queryRepairFormByRepairerId(userId);

        if(repairForms != null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,repairForms);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/updateRepairForm",method = RequestMethod.POST)
    public String updateRepairForm(RepairForm repairForm,HttpSession httpSession){
        Map<String,Object> responseMap = new HashMap<String, Object>();
        if(!checkIsLogin(httpSession)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo != null && (userInfo.getUserRule() & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.FREEZE_STATUS);
            return gson.toJson(responseMap);
        }
        if(userInfo != null && ((userInfo.getUserRule() >> 7) & 1 ) == 0){
            responseMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE, EnumInfo.NO_JURISDICTION);
        }
        RepairForm queryRepairForm = repairService.queryRepairFormByRepairId(repairForm.getRepairId());
        if(queryRepairForm != null && queryRepairForm.getCurrentStatus() == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        if(repairService.updateRepairForm(repairForm)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return gson.toJson(responseMap);
    }
    private Boolean checkIsLogin(HttpSession httpSession){
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo != null)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
    @RequestMapping(value = "/queryAllRepairForms",method = RequestMethod.GET)
    public String queryAllRepairForms(HttpSession httpSession){
        Map<String,Object> responseMap = new HashMap<String, Object>();
        if(!checkIsLogin(httpSession)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if((userInfo.getUserRule() & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.FREEZE_STATUS);
            return gson.toJson(responseMap);
        }
        if(((userInfo.getUserRule() >> 5) & 1 ) == 0){
            responseMap.put(EnumInfo.RESULT, EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE, EnumInfo.NO_JURISDICTION);
        }
        List<RepairForm>repairForms = repairService.queryRepairFormAll();
        if(repairForms != null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,repairForms);
            return gson.toJson(repairForms);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/queryThreeRepairForms",method = RequestMethod.GET)
    public String queryqueryThreeRepairForms(){
        Map<String,Object> responseMap = new HashMap<String, Object>();
        List<RepairForm>repairForms = repairService.queryRepairFormAll();
        if(repairForms == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
            return gson.toJson(responseMap);
        }
        if(repairForms.size() <=3 ){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,repairForms);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
        responseMap.put(EnumInfo.MESSAGE,repairForms.subList(0,3));
        return gson.toJson(responseMap);
    }
}
