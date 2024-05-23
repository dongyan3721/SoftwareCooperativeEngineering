package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.pojo.entity.BStudentPerformanceStudent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/23-21:31:29
 */
@Mapper
public interface StudentPerformanceStudentMapper {

    void insert(BStudentPerformanceStudent studentPerformanceStudent);
}
