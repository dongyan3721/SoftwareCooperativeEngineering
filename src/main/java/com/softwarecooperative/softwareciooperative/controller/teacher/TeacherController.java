package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.github.pagehelper.PageHelper;
import com.softwarecooperative.softwareciooperative.framework.annotation.AccessWithoutVerification;
import com.softwarecooperative.softwareciooperative.framework.exception.service.LoginFailedException;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.framework.net.TableDataInfo;
import com.softwarecooperative.softwareciooperative.pojo.dto.ChangePasswordDTO;
import com.softwarecooperative.softwareciooperative.pojo.dto.TeacherLoginDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterSettings;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;
import com.softwarecooperative.softwareciooperative.pojo.vo.NotificationVO;
import com.softwarecooperative.softwareciooperative.service.LoginService;
import com.softwarecooperative.softwareciooperative.service.TeacherService;
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
@RequestMapping("/teacher")
@Tag(name = "教师相关接口")
@Slf4j
public class TeacherController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/login")
    @Operation(summary = "教师登录")
    @AccessWithoutVerification
    public AjaxResult teacherLogin(@RequestBody TeacherLoginDTO teacherLoginDTO) throws LoginFailedException {
        BTeacher teacher = new BTeacher();
        BeanUtils.copyProperties(teacherLoginDTO, teacher);
        BTeacher res = loginService.teacherLogin(teacher);
        // 获取token
        String token = JwtUtils.sign(String.valueOf(res.getTeacherId()));
        AjaxResult body = AjaxResult.success(res);
        body.put("token", token);
        return body;
    }

    @PutMapping("")
    @Operation(summary = "教师修改个人信息")
    public AjaxResult modifyTeacher(@RequestBody BTeacher bTeacher) {
        teacherService.modifyTeacher(bTeacher);
        return AjaxResult.success();
    }

    @GetMapping("/{teacherId}")
    @Operation(summary = "按id查询教师")
    public AjaxResult getById(@PathVariable Integer teacherId) {
        BTeacher teacher = teacherService.getTeacherById(teacherId);
        return AjaxResult.success(teacher);
    }

    @PostMapping("/changePassword")
    @Operation(summary = "修改密码")
    public AjaxResult changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        teacherService.changePassword(changePasswordDTO);
        return AjaxResult.success();
    }
}
