package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.annotation.AccessWithoutVerification;
import com.softwarecooperative.softwareciooperative.framework.exception.service.LoginFailedException;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;
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
@RequestMapping("/teacher")
@Tag(name = "教师登录相关接口")
@Slf4j
public class TeacherLoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @Operation(summary = "教师登录")
    @AccessWithoutVerification
    public AjaxResult teacherLogin(@RequestBody BTeacher teacher) throws LoginFailedException {
        BTeacher res = loginService.teacherLogin(teacher);
        // 获取token
        String token = JwtUtils.sign(String.valueOf(res.getTeacherId()));
        AjaxResult body = AjaxResult.success(res);
        body.put("token", token);
        return body;
    }
}
