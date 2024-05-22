package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.pojo.vo.NotificationVO;

import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/17-10:17:08
 */
public interface NotificationService {

    void sendNotifToOneTeacher(Integer sourceRole, Integer teacherId, String message) throws IOException;

    void sendNotifToOneStudent(Integer sourceRole, Integer studentId, String message) throws IOException;

    void sendNotifToStudents(Integer sourceRole, List<Integer> ids, String message) throws IOException;

    void confirm(Integer noticeId, Integer role);

    void allRead(Integer role);

    PageResult<NotificationVO> pageSelect(Integer page, Integer pageSize, Integer role);
}
