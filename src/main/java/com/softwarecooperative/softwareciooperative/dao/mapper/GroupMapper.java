package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/16-15:08:33
 */
@Mapper
public interface GroupMapper {
    List<BGroup> selectByCond(BGroup bGroup);

    @InjectSnowFlakeId
    void insert(BGroup bGroup);
}