package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.dao.mapper.CourseMapper;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.BCourse;
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
 * @Date 2024/5/24-15:28:33
 */
@RestController
@RequestMapping("/teacher/course")
@Tag(name = "教师端课程相关接口")
@Slf4j
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("")
    @Operation(summary = "获取课程列表")
    public AjaxResult getAllCourse() {
        List<BCourse> res = courseMapper.selectAll();
        return AjaxResult.success(res);
    }
}
