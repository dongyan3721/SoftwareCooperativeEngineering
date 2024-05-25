package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-11:27:49
 */
public interface ClassService {

    List<BClass> getTeacherClass();

    void updateClassPhase(Integer classId, Integer phase);

    void newClass(BClass clazz);

    void deleteClass(Integer classId);

    void update(BClass clazz);

    BClass getClassByClassId(Integer classId);
}
