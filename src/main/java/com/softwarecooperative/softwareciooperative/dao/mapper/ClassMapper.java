package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassMapper {
    List<BClass> selectAll();
    List<BClass> selectByCond(BClass bClass);
    BClass selectOne(BClass bClass);
    @InjectSnowFlakeId
    void insert(BClass bClass);
    void update(BClass bClass);
    void delete(@Param("id") Integer id);

    Integer selectClassStatus(@Param("classId") Integer classId);
}
