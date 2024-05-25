package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.ClearAllCache;
import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface ClassMapper {
    List<BClass> selectAll();

    @Cacheable(cacheNames = "teacherAllClass", condition = "#bClass.teacherId != null", key = "#bClass.teacherId")
    List<BClass> selectByCond(BClass bClass);

    @Cacheable(cacheNames = "clazz", condition = "#bClass.classId != null ", key = "#bClass.classId")
    BClass selectOne(BClass bClass);

    @InjectSnowFlakeId
    @ClearAllCache({"teacherAllClass"})
    void insert(BClass bClass);

    @CacheEvict(cacheNames = "clazz", key = "bClass.classId")
    @ClearAllCache({"teacherAllClass"})
    void update(BClass bClass);

    @CacheEvict(cacheNames = "clazz", key = "id")
    @ClearAllCache({"teacherAllClass"})
    void delete(@Param("id") Integer id);

    @CacheEvict(cacheNames = "clazz", key = "#classId")
    @ClearAllCache({"teacherAllClass"})
    Integer selectClassStatus(@Param("classId") Integer classId);
}
