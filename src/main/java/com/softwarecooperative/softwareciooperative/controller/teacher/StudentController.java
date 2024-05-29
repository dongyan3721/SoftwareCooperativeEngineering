package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.framework.net.TableDataInfo;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/29-19:38:05
 */
@RestController("TeacherStudentController")
@RequestMapping("/teacher/student")
@Tag(name = "教师相关接口")
@Slf4j
public class StudentController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("")
    @Operation(summary = "教师添加单个学生")
    public AjaxResult addOneStudent(@RequestBody BStudent student){
        teacherService.addOneStudent(student);
        return AjaxResult.success();
    }
    @DeleteMapping("/{studentId}")
    @Operation(summary = "教师删除单个学生")
    public AjaxResult deleteOneStudent(@PathVariable Integer studentId){
        teacherService.deleteOneStudent(studentId);
        return AjaxResult.success();
    }

    @PutMapping("")
    @Operation(summary = "教师修改学生信息")
    public AjaxResult modifyStudent(@RequestBody BStudent student){
        teacherService.modifyStudent(student);
        return AjaxResult.success();
    }

    @GetMapping("")
    @Operation(summary = "教师分页查询学生")
    public TableDataInfo pageSelect(Integer pageNum, Integer pageSize, Integer classId, String studentName, Integer studentId){
        PageResult<BStudent> res = teacherService.pageSelect(pageNum, pageSize, classId, studentName, studentId);
        return TableDataInfo.success(res.getRecords(), res.getTotal());
    }
}
