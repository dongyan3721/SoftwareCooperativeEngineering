package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-19:39:25
 */
@Mapper
public interface ClassTaskMapper {

    List<BClassTask> selectByCond(BClassTask classTask);

    BClassTask selectOne(BClassTask query);

    @InjectSnowFlakeId
    void insert(BClassTask task);

    void update(BClassTask task);
}
