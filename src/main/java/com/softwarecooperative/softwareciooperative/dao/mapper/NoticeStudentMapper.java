package com.softwarecooperative.softwareciooperative.dao.mapper;

import com.github.pagehelper.Page;
import com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId;
import com.softwarecooperative.softwareciooperative.pojo.entity.BNoticeStudent;
import com.softwarecooperative.softwareciooperative.pojo.vo.NotificationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/17-10:32:11
 */
@Mapper
public interface NoticeStudentMapper {

    @InjectSnowFlakeId
    void insert(BNoticeStudent noticeStudent);

    Page<NotificationVO> pageSelect(@Param("studentId") Integer studentId);
}
