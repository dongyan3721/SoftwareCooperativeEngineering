package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BNoticeReceiveStudent;
import com.softwarecooperative.softwareciooperative.pojo.entity.BNoticeReceiveTeacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/17-10:32:50
 */
@Mapper
public interface NoticeReceiveTeacherMapper {

    void insertOne(BNoticeReceiveTeacher noticeReceiveTeacher);

    void insertBatch(List<BNoticeReceiveTeacher> teas);
}
