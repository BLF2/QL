package net.blf2.controller;

import com.google.gson.Gson;
import net.blf2.model.entity.RepairForm;
import net.blf2.model.entity.ScheduleInfo;
import net.blf2.model.entity.UserInfo;
import net.blf2.model.service.interfaces.IRepairService;
import net.blf2.model.service.interfaces.IScheduleService;
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
 * Created by blf2 on 16-8-28.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/ScheduleInfo",produces = "text/plain;charset=UTF-8")
public class ScheduleInfoController {
    @Autowired
    @Qualifier("ScheduleServiceImpl")
    private IScheduleService scheduleService;

    @Autowired
    @Qualifier("RepairServiceImpl")
    private IRepairService repairService;
    
    private static Gson gson = new Gson();

    @RequestMapping(value = "/addScheduleInfo",method = RequestMethod.POST)
    public String addScheduleInfo(@RequestParam(name = "handlerUserId",required = true)String handlerUserId,
                                  @RequestParam(name = "repairId",required = true)String repairId,
                                  @RequestParam(name = "scheuleStatus",required = true)String scheuleStatus,
                                  HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 10 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        Date changeTime = new Date();
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.setHandlerUserId(handlerUserId);
        scheduleInfo.setChangeTime(changeTime);
        scheduleInfo.setRepairId(repairId);
        scheduleInfo.setScheuleStatus(scheuleStatus);
        ScheduleInfo reScheduleInfo = scheduleService.addNewScheduleInfo(scheduleInfo);
            if (scheduleInfo != null) {
                RepairForm repairForm = repairService.queryRepairFormByRepairId(reScheduleInfo.getRepairId());
                if (repairForm != null) {
                    if (repairForm.getCurrentStatus() != 0) {
                        repairForm.setCurrentStatus(0);
                        if (repairService.updateRepairForm(repairForm)) {
                            responseMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
                            responseMap.put(EnumInfo.MESSAGE, EnumInfo.SUCCESS_INFO);
                            return gson.toJson(responseMap);
                        }
                    }
                    responseMap.put(EnumInfo.RESULT, EnumInfo.SUCCESS);
                    responseMap.put(EnumInfo.MESSAGE, EnumInfo.SUCCESS_INFO);
                    return gson.toJson(responseMap);
                }
            }

        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/updateScheduleInfo",method = RequestMethod.POST)
    public String updateScheduleInfo(ScheduleInfo scheduleInfo,HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 10 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        if(scheduleService.updateScheduleInfo(scheduleInfo)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/deleteScheduleInfo",method = RequestMethod.GET)
    public String deleteScheduleInfoByScheduleId(@RequestParam(name = "scheduleId",required = true)String scheduleId,
                                                 HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 12 & 1) == 0 || (userInfo.getUserRule() >> 10 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
            return gson.toJson(responseMap);
        }
        ScheduleInfo scheduleInfo = scheduleService.queryScheduleInfoByScheduleId(scheduleId);
        if(scheduleInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        if(scheduleService.deleteScheduleInfo(scheduleInfo)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/queryScheduleInfoByScheduleInfoId",method = RequestMethod.GET)
    public String queryScheduleInfoByScheduleInfoId(@RequestParam(name = "scheduleInfoId",required = true)String scheduleInfoId,
                                                    HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 9 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
            return gson.toJson(responseMap);
        }
        ScheduleInfo scheduleInfo = scheduleService.queryScheduleInfoByScheduleId(scheduleInfoId);
        if(scheduleInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
        responseMap.put(EnumInfo.MESSAGE,scheduleInfo);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/queryScheduleInfoByRepairId",method = RequestMethod.GET)
    public String queryScheduleInfoByRepairId(@RequestParam(name = "repairId",required = true)String repairId,
                                              HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 9 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
            return gson.toJson(responseMap);
        }
        List<ScheduleInfo>scheduleInfos = scheduleService.queryScheduleInfoByRepairId(repairId);
        if(scheduleInfos == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
        responseMap.put(EnumInfo.MESSAGE,scheduleInfos);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/queryScheduleInfoByHandlerUserId",method = RequestMethod.GET)
    public String queryScheduleInfoByHandlerUserId(@RequestParam(name = "handlerUserId",required = true)String handlerUserId,
                                                   HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() >> 9 & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
            return gson.toJson(responseMap);
        }
        List<ScheduleInfo>scheduleInfos = scheduleService.queryScheduleInfoByHandlerUserId(handlerUserId);
        if(scheduleInfos == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
        responseMap.put(EnumInfo.MESSAGE,scheduleInfos);
        return gson.toJson(responseMap);
    }
}
