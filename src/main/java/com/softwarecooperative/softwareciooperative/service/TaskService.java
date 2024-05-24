package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.pojo.dto.MarkPerformanceDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassTask;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudentTaskSubmit;

import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-10:35:57
 */
public interface TaskService {

    List<BClassTask> getClassAllTask(Integer classId);

    List<BStudentTaskSubmit> getGroupAllTask(Integer groupId, Integer taskId);

    void submitSubTask(Integer recordId, String submitLink) throws IOException;

    void updateSubTask(Integer recordId, String submitLink);

    void updateSubTaskDescription(Integer recordId, String description) throws IOException;

    BStudentTaskSubmit getSubtaskSubmit(Integer recordId);

    BStudentTaskSubmit getMainTaskSubmit(Integer groupId, Integer taskId);

    void markPerformanceByStudent(MarkPerformanceDTO mark) throws IOException;

    void markPerformanceByTeacher(MarkPerformanceDTO mark) throws IOException;

    void updateTask(BClassTask task);
}
