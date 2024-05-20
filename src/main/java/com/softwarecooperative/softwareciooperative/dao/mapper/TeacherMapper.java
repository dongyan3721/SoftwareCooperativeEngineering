package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:26:29
 */
@Mapper
public interface TeacherMapper {
    BTeacher selectOne(BTeacher teacher);

    void update(BTeacher bTeacher);

    Boolean ifExist(@Param("teacherId") Integer teacherId);
}
