package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroup;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/16-15:08:33
 */
@Mapper
public interface GroupMapper {
    List<BGroup> selectByCond(BGroup bGroup);

    BGroup selectOne(BGroup bGroup);

    BStudent selectLeaderByGroupId(@Param("groupId") Integer groupId);

    @InjectSnowFlakeId
    void insert(BGroup bGroup);

    void delete(@Param("groupId") Integer groupId);

    void update(BGroup bGroup);
}
