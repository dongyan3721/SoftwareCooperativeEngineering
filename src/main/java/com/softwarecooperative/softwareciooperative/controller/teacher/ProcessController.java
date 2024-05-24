package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.vo.GroupAndPhaseVO;
import com.softwarecooperative.softwareciooperative.service.ProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-19:18:40
 */
@RestController("TeacherProcessController")
@RequestMapping("/teacher/process")
@Tag(name = "教师团队进度相关接口")
@Slf4j
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping("")
    @Operation(summary = "获取教学班所有团队的进度")
    public AjaxResult getGroupPhase(Integer classId) {
        List<GroupAndPhaseVO> phase = processService.getClassGroupPhases(classId);
        return AjaxResult.success(phase);
    }
}
