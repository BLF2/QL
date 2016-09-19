package net.blf2.model.service.impl;

import net.blf2.model.dao.interfaces.IRepairCommentDao;
import net.blf2.model.entity.RepairComment;
import net.blf2.model.service.interfaces.IRepairCommentService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by blf2 on 16-9-17.
 */
@Service("CommentServiceImpl")
public class CommentServiceImpl implements IRepairCommentService {

    @Autowired
    @Qualifier("RepairCommentDaoImpl")
    private IRepairCommentDao iRepairCommentDAO;

    public IRepairCommentDao getiRepairCommentDAO() {
        return iRepairCommentDAO;
    }

    public void setiRepairCommentDAO(IRepairCommentDao iRepairCommentDAO) {
        this.iRepairCommentDAO = iRepairCommentDAO;
    }


    @Override
    public RepairComment insertRepairComment(RepairComment repairComment) {
     //   repairComment.setCommentText(StringEscapeUtils.escapeEcmaScript(repairComment.getCommentText()));
     //   System.out.println("In service commentText = "+repairComment.getCommentText());
        return iRepairCommentDAO.insertData(repairComment);
    }

    @Override
    public RepairComment queryRepairCommentById(String repairCommentId) {
        return iRepairCommentDAO.queryRepairCommentById(repairCommentId);
    }

    @Override
    public List<RepairComment> queryRepairCommentByRepairId(String repairId) {
        return iRepairCommentDAO.queryCommentByRepairId(repairId);
    }

    @Override
    public List<RepairComment> queryRepairCommentAll() {
        return iRepairCommentDAO.queryDataAll();
    }

    @Override
    public Boolean updateRepairComment(RepairComment repairComment) {
     //   repairComment.setCommentText(StringEscapeUtils.escapeEcmaScript(repairComment.getCommentText()));
        return iRepairCommentDAO.updateData(repairComment);
    }

    @Override
    public Boolean deleteRepairComment(RepairComment repairComment) {
        return iRepairCommentDAO.deleteData(repairComment);
    }
}
