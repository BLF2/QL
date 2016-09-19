package net.blf2.model.service.interfaces;

import net.blf2.model.entity.ClassificationInfo;

import java.util.List;

/**
 * Created by t_mengxh on 2016/7/27.
 */
public interface IClassificationService {
    public ClassificationInfo addNewClassification(String classificationName);
    public Boolean isRepetitiveOfClassificationName(String classificationName);
    public Boolean updateClassificationInfo(String classificationId,String classificationName);
    public ClassificationInfo queryClassificationInfoByClassificationId(String ClassificationId);
    public Boolean deleteClassificationInfoByClassificationId(String classificationId);
    public List<ClassificationInfo> queryClassificationInfoAll();
}
