package com.softwarecooperative.softwareciooperative.controller.student;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
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
 * @Date 2024/5/22-10:19:46
 */
@RestController("StudentTaskController")
@RequestMapping("/student/task")
@Tag(name = "学生任务相关接口")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("")
    @Operation(summary = "获取教学班所有任务")
    public AjaxResult getClassAllTask(Integer classId) {
        List<BClassTask> res = taskService.getClassAllTask(classId);
        return AjaxResult.success(res);
    }

    @GetMapping("/subTasks")
    @Operation(summary = "获取团队任务的分任务")
    public AjaxResult getGroupAllTask(Integer groupId, Integer taskId) {
        List<BStudentTaskSubmit> res = taskService.getGroupAllTask(groupId, taskId);
        return AjaxResult.success(res);
    }

    @PostMapping("")
    @Operation(summary = "提交分任务")
    public AjaxResult submitSubTask(Integer recordId, String submitLink) throws IOException {
        taskService.submitSubTask(recordId, submitLink);
        return AjaxResult.success();
    }

    @PutMapping("")
    @Operation(summary = "修改分任务")
    public AjaxResult updateSubTask(@RequestBody BStudentTaskSubmit submit) {
        taskService.updateSubTask(submit.getRecordId(), submit.getSubmitLink());
        return AjaxResult.success();
    }

    @PutMapping("/description")
    @Operation(summary = "当前阶段经理修改成员任务描述")
    public AjaxResult updateSubTaskDescription(@RequestBody BStudentTaskSubmit update) throws IOException {
        taskService.updateSubTaskDescription(update.getRecordId(), update.getTaskHandlerWork());
        return AjaxResult.success();
    }

}
