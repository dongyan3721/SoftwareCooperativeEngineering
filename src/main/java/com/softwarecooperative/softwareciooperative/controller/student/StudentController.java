package com.softwarecooperative.softwareciooperative.controller.student;

import com.softwarecooperative.softwareciooperative.framework.annotation.AccessWithoutVerification;
import com.softwarecooperative.softwareciooperative.framework.exception.service.LoginFailedException;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.service.LoginService;
import com.softwarecooperative.softwareciooperative.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    @Operation(summary = "学生登录")
    @AccessWithoutVerification
    public AjaxResult teacherLogin(@RequestBody BStudent student) throws LoginFailedException {
        BStudent res = loginService.studentLogin(student);
        // 获取token
        String token = JwtUtils.sign(String.valueOf(res.getStudentId()));
        AjaxResult body = AjaxResult.success(res);
        body.put("token", token);
        return body;
    }
}
