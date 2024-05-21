package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.github.pagehelper.Page;
import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroupAppealIn;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealInVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/19-20:22:34
 */
@Mapper
public interface GroupAppealInMapper {

    @InjectSnowFlakeId
    void insert(BGroupAppealIn appeal);

    BGroupAppealIn selectOne(BGroupAppealIn query);

    void delete(@Param("appealId") Integer appealId);

    void deleteByStuId(@Param("studentId") Integer studentId);

    Page<AppealInVO> selectAppealInVOByGroupId(@Param("groupId") Integer groupId);

    List<Integer> selectGrpIdByStuId(@Param("studentId") Integer studentId);
}
