package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:26:29
 */
@Mapper
public interface TeacherMapper {

    @Cacheable(cacheNames = "teacher", condition = "#teacher.teacherId != null", key = "#teacher.teacherId")
    BTeacher selectOne(BTeacher teacher);

    @CacheEvict(cacheNames = "teacher", key = "#bTeacher.teacherId")
    void update(BTeacher bTeacher);

    @Cacheable(cacheNames = "teacherExist", key = "#teacherId")
    Boolean ifExist(@Param("teacherId") Integer teacherId);
}
