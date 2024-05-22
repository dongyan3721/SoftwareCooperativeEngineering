package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BNoticeReceiveStudent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/17-10:32:22
 */
@Mapper
public interface NoticeReceiveStudentMapper {

    void insertOne(BNoticeReceiveStudent noticeReceiveStudent);

    void insertBatch(List<BNoticeReceiveStudent> stus);

    void update(BNoticeReceiveStudent newEntity);
}
