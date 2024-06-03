package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.github.pagehelper.Page;
import com.softwarecooperative.softwareciooperative.framework.annotation.ClearAllCache;
import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.vo.NotificationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:26:38
 */
@Mapper
public interface StudentMapper {

    @Cacheable(cacheNames = "student", condition = "#student.studentId != null", key = "#student.studentId")
    BStudent selectOne(BStudent student);

    @CacheEvict(cacheNames = "student", key = "#student.studentId")
    @ClearAllCache({"studentsInGroup", "pageStudent"})
    void update(BStudent student);

    List<BStudent> selectByCond(BStudent student);

    @CacheEvict(cacheNames = "student", allEntries = true)
    @ClearAllCache({"studentsInGroup", "pageStudent"})
    void deleteGroup(@Param("groupId") Integer groupId);

    @CacheEvict(cacheNames = "student", allEntries = true)
    @ClearAllCache({"studentsInGroup", "pageStudent"})
    void exitGroup(@Param("targetStuId") Integer targetStuId);

    @Cacheable(cacheNames = "studentsInGroup", condition = "#student.studentGroup != null", key = "#student.studentGroup")
    List<Integer> selectIdByCond(BStudent student);

    @InjectSnowFlakeId
    @ClearAllCache({"pageStudent"})
    void insertOne(BStudent student);

    @CacheEvict(cacheNames = "student", key = "#studentId")
    @ClearAllCache({"studentsInGroup", "pageStudent"})
    void deleteOne(Integer studentId);

    Page<BStudent> pageSelect(BStudent query);
}
