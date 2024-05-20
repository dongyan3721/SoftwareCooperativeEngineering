package com.softwarecooperative.softwareciooperative.service;

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

}
