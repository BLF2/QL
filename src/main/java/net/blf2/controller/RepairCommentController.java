package net.blf2.controller;

import com.google.gson.Gson;
import net.blf2.model.entity.RepairComment;
import net.blf2.model.entity.UserInfo;
import net.blf2.model.service.interfaces.IRepairCommentService;
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
 * Created by blf2 on 16-9-17.
 */
@Controller
@ResponseBody
@RequestMapping(value = "/RepairComment",produces = "text/plain;charset=UTF-8")
public class RepairCommentController {
    @Autowired
    @Qualifier("CommentServiceImpl")
    private IRepairCommentService commentService;

    @Autowired
    @Qualifier("RepairServiceImpl")
    private IRepairService repairService;

    private static Gson gson = new Gson();

    @RequestMapping(value = "/addRepairComment",method = RequestMethod.POST)
    public String addRepairComment(@RequestParam(name = "repairId",required = true)String repairId,
                                   @RequestParam(name = "commentText")String commentText,
                                   HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        RepairComment repairComment = new RepairComment();
        repairComment.setRepairId(repairId);
        repairComment.setCommentText(commentText);
        repairComment.setCommentDateTime(new Date());
        repairComment.setSubmiterId(userInfo.getUserId());
        if((repairComment = commentService.insertRepairComment(repairComment)) != null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/updateRepairComment",method = RequestMethod.POST)
    public String updateRepairComment(@RequestParam(name = "repairId",required = true)String repairId,
                                      @RequestParam(name = "commetText",required = true)String commetText,
                                      @RequestParam(name = "repairCommentId",required = true)String repairCommentId,
                                      @RequestParam(name = "userId",required = true)String userId
                                      ,HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (net.blf2.model.entity.UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() & 1) == 0 || !userInfo.getUserId().equals(userId)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        RepairComment repairComment = commentService.queryRepairCommentById(repairCommentId);
        if(repairComment == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
            return gson.toJson(responseMap);
        }
        repairComment.setCommentText(commetText);
        if(commentService.updateRepairComment(repairComment)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/deleteRepairComment",method = RequestMethod.GET)
    public String deleteRepairComment(@RequestParam(name = "userId",required = true)String userId,
                                      @RequestParam(name = "repairCommentId",required = true)
                                      String repairCommentId,HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (net.blf2.model.entity.UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() & 1) == 0 || !userInfo.getUserId().equals(userId)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        RepairComment repairComment = commentService.queryRepairCommentById(repairCommentId);
        if(repairComment == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
            return gson.toJson(responseMap);
        }
        if(commentService.deleteRepairComment(repairComment)){
            responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.SUCCESS_INFO);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
        responseMap.put(EnumInfo.MESSAGE,EnumInfo.DB_RETURN_VALUE_ERROR);
        return gson.toJson(responseMap);
    }
    @RequestMapping(value = "/queryRepairCommentByRepairId",method = RequestMethod.GET)
    public String queryRepairCommentByRepairId(@RequestParam(name = "repairId",required = true)String repairId,
                                               HttpSession httpSession){
        Map<String,Object>responseMap = new HashMap<String, Object>();
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
        if(userInfo == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.HAS_NOT_LOGINED);
            return gson.toJson(responseMap);
        }
        if((userInfo.getUserRule() & 1) == 0){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NO_JURISDICTION);
            return gson.toJson(responseMap);
        }
        List<RepairComment> repairComments =  commentService.queryRepairCommentByRepairId(repairId);
        if(repairComments == null){
            responseMap.put(EnumInfo.RESULT,EnumInfo.FAIL);
            responseMap.put(EnumInfo.MESSAGE,EnumInfo.NOT_FIND_INFO);
            return gson.toJson(responseMap);
        }
        responseMap.put(EnumInfo.RESULT,EnumInfo.SUCCESS);
        responseMap.put(EnumInfo.MESSAGE,repairComments);
        return gson.toJson(responseMap);
    }

}
