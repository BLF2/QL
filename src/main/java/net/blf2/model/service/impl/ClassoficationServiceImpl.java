package net.blf2.model.service.impl;

import net.blf2.model.dao.interfaces.IClassificationInfoDao;
import net.blf2.model.entity.ClassificationInfo;
import net.blf2.model.service.interfaces.IClassificationService;
import net.blf2.util.GlobalStaticTools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/30.
 */
@Service("ClassoficationServiceImpl")
public class ClassoficationServiceImpl implements IClassificationService{
    @Autowired
    @Qualifier("ClassificationInfoDaoImpl")
    private IClassificationInfoDao iClassificationInfoDao;

    private static Logger logger = Logger.getLogger(ClassoficationServiceImpl.class);
    public IClassificationInfoDao getiClassificationInfoDao() {
        return iClassificationInfoDao;
    }

    public void setiClassificationInfoDao(IClassificationInfoDao iClassificationInfoDao) {
        this.iClassificationInfoDao = iClassificationInfoDao;
    }

    public ClassificationInfo addNewClassification(String classificationName) {
        return GlobalStaticTools.checkStringIsNullOrIsEmpty(classificationName) ? null :
                iClassificationInfoDao.insertData(new ClassificationInfo(classificationName));
    }

    public Boolean isRepetitiveOfClassificationName(String classificationName) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(classificationName))
            return Boolean.FALSE;
        if(iClassificationInfoDao.queryDataByClassificationName(classificationName) != null)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    public Boolean updateClassificationInfo(String classificationId, String classificationName) {
        ClassificationInfo updateClassificationInfo = new ClassificationInfo();
        updateClassificationInfo.setClassifocationId(classificationId);
        updateClassificationInfo.setClassficationName(classificationName);
        return iClassificationInfoDao.updateData(updateClassificationInfo);
    }

    public ClassificationInfo queryClassificationInfoByClassificationId(String classificationId) {
        return GlobalStaticTools.checkStringIsNullOrIsEmpty(classificationId) ? null :
                iClassificationInfoDao.queryDataByClassificationId(classificationId);
    }

    public Boolean deleteClassificationInfoByClassificationId(String classificationId) {
        if(GlobalStaticTools.checkStringIsNullOrIsEmpty(classificationId))
            return Boolean.FALSE;
        ClassificationInfo deleteClassificationInfo = this.queryClassificationInfoByClassificationId(classificationId);
        return deleteClassificationInfo == null ? Boolean.FALSE : iClassificationInfoDao.deleteData(deleteClassificationInfo);
    }

    public List<ClassificationInfo> queryClassificationInfoAll() {
        return iClassificationInfoDao.queryDataAll();
    }

}
