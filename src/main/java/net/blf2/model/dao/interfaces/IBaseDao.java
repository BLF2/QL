package net.blf2.model.dao.interfaces;

import java.util.List;
import java.util.UUID;

/**
 * Created by t_mengxh on 2016/7/24.
 */
public interface IBaseDao <T> {
    public T insertData(T t);
    public Boolean deleteData(T t);
    public Boolean updateData(T t);
    public List<T> queryDataAll();
}
