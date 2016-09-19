package net.blf2.model.dao.interfaces;

import net.blf2.model.entity.ClassificationInfo;

/**
 * Created by t_mengxh on 2016/7/24.
 */
public interface IClassificationInfoDao extends IBaseDao<ClassificationInfo> {
    public ClassificationInfo queryDataByClassificationId(String classificationId);
    public ClassificationInfo queryDataByClassificationName(String classificationName);
}
