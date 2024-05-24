package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.dto.UpdateClassPhaseDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import com.softwarecooperative.softwareciooperative.service.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-11:20:17
 */
@RestController("TeacherClassController")
@RequestMapping("/teacher/class")
@Tag(name = "教师教学班相关接口")
@Slf4j
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping("")
    @Operation(summary = "新增教学班")
    public AjaxResult newClass(@RequestBody BClass clazz) {
        classService.newClass(clazz);
        return AjaxResult.success();
    }

    @DeleteMapping("/{classId}")
    @Operation(summary = "删除教学班")
    public AjaxResult deleteClass(@PathVariable Integer classId) {
        classService.deleteClass(classId);
        return AjaxResult.success();
    }

    @PutMapping("")
    @Operation(summary = "修改教学班信息")
    public AjaxResult updateClass(@RequestBody BClass clazz) {
        classService.update(clazz);
        return AjaxResult.success();
    }

    @GetMapping("/teacherAll")
    @Operation(summary = "获取教师所有教学班")
    public AjaxResult getTeacherClass() {
        List<BClass> res = classService.getTeacherClass();
        return AjaxResult.success(res);
    }

    @PutMapping("/phase")
    @Operation(summary = "修改教学班阶段")
    public AjaxResult updateClassPhase(@RequestBody UpdateClassPhaseDTO dto) {
        classService.updateClassPhase(dto.getClassId(), dto.getPhase());
        return AjaxResult.success();
    }
}
