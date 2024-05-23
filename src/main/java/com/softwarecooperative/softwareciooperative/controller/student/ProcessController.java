package com.softwarecooperative.softwareciooperative.controller.student;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.service.ProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-19:18:40
 */
@RestController
@RequestMapping("/student/process")
@Tag(name = "团队进度相关接口")
@Slf4j
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping("")
    @Operation(summary = "获取指定团队的进度")
    public AjaxResult getGroupPhase(Integer groupId) {
        Integer phase = processService.getGroupPhase(groupId);
        return AjaxResult.success(phase);
    }
}
