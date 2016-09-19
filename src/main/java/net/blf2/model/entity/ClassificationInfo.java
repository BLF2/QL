package net.blf2.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Entity
@Table(name="ClassificationInfo")
public class ClassificationInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String classifocationId;//匪类ID
    @Column(name = "classficationName",nullable = false)
    private String classficationName;//分类名

    public ClassificationInfo() {
    }

    public ClassificationInfo(String classficationName) {
        this.classficationName = classficationName;
    }

    public String getClassifocationId() {
        return classifocationId;
    }

    public void setClassifocationId(String classifocationId) {
        this.classifocationId = classifocationId;
    }

    public String getClassficationName() {
        return classficationName;
    }

    public void setClassficationName(String classficationName) {
        this.classficationName = classficationName;
    }
}
