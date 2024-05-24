package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.dto.MarkPerformanceDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassTask;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudentTaskSubmit;
import com.softwarecooperative.softwareciooperative.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/24-10:07:19
 */
@RestController("TeacherTaskController")
@RequestMapping("/teacher/task")
@Tag(name = "教师任务相关接口")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PutMapping("")
    @Operation(summary = "修改阶段任务")
    public AjaxResult updateTask(@RequestBody BClassTask task) {
        taskService.updateTask(task);
        return AjaxResult.success();
    }

    @GetMapping("")
    @Operation(summary = "获取教学班所有任务")
    public AjaxResult getClassAllTask(Integer classId) {
        List<BClassTask> res = taskService.getClassAllTask(classId);
        return AjaxResult.success(res);
    }

    @GetMapping("/mainTask")
    @Operation(summary = "查看阶段总任务提交")
    public AjaxResult getMainTaskSubmit(Integer groupId, Integer taskId) {
        BStudentTaskSubmit res = taskService.getMainTaskSubmit(groupId, taskId);
        return AjaxResult.success(res);
    }

    @PostMapping("/performance")
    @Operation(summary = "教师给组员打分")
    public AjaxResult markPerformance(@RequestBody MarkPerformanceDTO mark) throws IOException {
        taskService.markPerformanceByTeacher(mark);
        return AjaxResult.success();
    }
}
