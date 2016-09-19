package net.blf2.model.service.impl;

import net.blf2.model.dao.interfaces.IRepairCommentDao;
import net.blf2.model.entity.RepairComment;
import net.blf2.model.service.interfaces.IRepairCommentService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by blf2 on 16-9-17.
 */
@Service("CommentServiceImpl")
public class CommentServiceImpl implements IRepairCommentService {

    @Autowired
    @Qualifier("RepairCommentDaoImpl")
    private IRepairCommentDao iRepairCommentDao;




    @Override
    public RepairComment insertRepairComment(RepairComment repairComment) {
     //   repairComment.setCommentText(StringEscapeUtils.escapeEcmaScript(repairComment.getCommentText()));
     //   System.out.println("In service commentText = "+repairComment.getCommentText());
        return iRepairCommentDao.insertData(repairComment);
    }

    @Override
    public RepairComment queryRepairCommentById(String repairCommentId) {
        return iRepairCommentDao.queryRepairCommentById(repairCommentId);
    }

    @Override
    public List<RepairComment> queryRepairCommentByRepairId(String repairId) {
        return iRepairCommentDao.queryCommentByRepairId(repairId);
    }

    @Override
    public List<RepairComment> queryRepairCommentAll() {
        return iRepairCommentDao.queryDataAll();
    }

    @Override
    public Boolean updateRepairComment(RepairComment repairComment) {
     //   repairComment.setCommentText(StringEscapeUtils.escapeEcmaScript(repairComment.getCommentText()));
        return iRepairCommentDao.updateData(repairComment);
    }

    @Override
    public Boolean deleteRepairComment(RepairComment repairComment) {
        return iRepairCommentDao.deleteData(repairComment);
    }
}
