package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.github.pagehelper.Page;
import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroupAppealLeader;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealLeaderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/16-15:10:13
 */
@Mapper
public interface GroupAppealLeaderMapper {
    void delete(@Param("appealId") Integer appealId);

    BGroupAppealLeader selectById(@Param("appealId") Integer appealId);

    Page<AppealLeaderVO> selectAppealLeaderVOByClass(@Param("classId") Integer classId);

    @InjectSnowFlakeId
    void insert(BGroupAppealLeader bGroupAppealLeader);

    BGroupAppealLeader selectOne(BGroupAppealLeader appealQuery);
}
