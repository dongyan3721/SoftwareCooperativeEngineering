package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.pojo.entity.BStudentPerformanceTeacher;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/6/5-13:29:26
 */
@Mapper
public interface StudentPerformanceTeacherMapper {

    void insert(BStudentPerformanceTeacher studentPerformanceTeacher);
}
