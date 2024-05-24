package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.pojo.entity.BCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/24-12:10:04
 */
@Mapper
public interface CourseMapper {

    BCourse selectById(@Param("courseId") Integer courseId);

    List<BCourse> selectAll();
}
