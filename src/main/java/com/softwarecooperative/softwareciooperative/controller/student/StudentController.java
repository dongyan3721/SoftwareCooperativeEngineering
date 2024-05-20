package com.softwarecooperative.softwareciooperative.controller.student;

import com.softwarecooperative.softwareciooperative.framework.annotation.AccessWithoutVerification;
import com.softwarecooperative.softwareciooperative.framework.exception.service.LoginFailedException;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.dto.ChangePasswordDTO;
import com.softwarecooperative.softwareciooperative.pojo.dto.StudentLoginDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;
import com.softwarecooperative.softwareciooperative.service.LoginService;
import com.softwarecooperative.softwareciooperative.service.StudentService;
import com.softwarecooperative.softwareciooperative.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:11:21
 */
@RestController
@RequestMapping("/student")
@Tag(name = "学生相关接口")
@Slf4j
public class StudentController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    @Operation(summary = "学生登录")
    @AccessWithoutVerification
    public AjaxResult teacherLogin(@RequestBody StudentLoginDTO studentLoginDTO) throws LoginFailedException {
        BStudent student = new BStudent();
        BeanUtils.copyProperties(studentLoginDTO, student);
        BStudent res = loginService.studentLogin(student);
        // 获取token
        String token = JwtUtils.sign(String.valueOf(res.getStudentId()));
        AjaxResult body = AjaxResult.success(res);
        body.put("token", token);
        return body;
    }
    @GetMapping("/{studentId}")
    @Operation(summary = "根据id查询学生")
    public AjaxResult getStudentById(@PathVariable Integer studentId){
        BStudent res = studentService.getStudentById(studentId);
        return AjaxResult.success(res);
    }
    @PutMapping("")
    @Operation(summary = "学生修改信息")
    public AjaxResult modifyStudent(@RequestBody BStudent student){
        studentService.modifyStudent(student);
        return AjaxResult.success();
    }

    @PutMapping("/changePassword")
    @Operation(summary = "修改学生密码")
    public AjaxResult modifyPswStudent(@RequestBody ChangePasswordDTO changePasswordDTO){
        studentService.modifyPswStudent(changePasswordDTO);
        return AjaxResult.success();
    }

}
