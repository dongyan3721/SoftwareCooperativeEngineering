package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.framework.annotation.ClearAllCache;
import com.softwarecooperative.softwareciooperative.pojo.dto.MarkPerformanceDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassTask;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudentTaskSubmit;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-10:35:57
 */
public interface TaskService {
    /**
     * 分任务相关接口
     */
    List<BStudentTaskSubmit> getGroupAllTask(Integer groupId, Integer taskId);

    BStudentTaskSubmit getSubtaskSubmit(Integer recordId);

    BStudentTaskSubmit getMainTaskSubmit(Integer groupId, Integer taskId);

    void submitSubTask(Integer recordId, String submitLink) throws IOException;

    void updateSubTask(Integer recordId, String submitLink);

    void updateSubTaskDescription(Integer recordId, String description) throws IOException;


    /**
     * 教学班任务相关接口
     */
    List<BClassTask> getClassAllTask(Integer classId);

    void updateTask(BClassTask task);

    /**
     * 打分相关接口
     */
    void markPerformanceByStudent(MarkPerformanceDTO mark) throws IOException;

    void markPerformanceByTeacher(MarkPerformanceDTO mark) throws IOException;
}
