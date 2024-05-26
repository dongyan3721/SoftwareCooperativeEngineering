package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudentTaskSubmit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-10:33:48
 */
@Mapper
public interface StudentTaskSubmitMapper {

    Integer selectGroupPhaseByGroupId(@Param("groupId") Integer groupId);

    List<BStudentTaskSubmit> selectByCond(BStudentTaskSubmit query);

    BStudentTaskSubmit selectOne(BStudentTaskSubmit query);

    void update(BStudentTaskSubmit newSubmit);

    @InjectSnowFlakeId
    void insertBatch(List<BStudentTaskSubmit> subtasks);
}
