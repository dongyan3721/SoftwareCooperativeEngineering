package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:26:38
 */
@Mapper
public interface StudentMapper {
    BStudent selectOne(BStudent student);
    void update(BStudent student);

    List<BStudent> selectByCond(BStudent student);

    void exitGroup(@Param("groupId") Integer groupId);
}
