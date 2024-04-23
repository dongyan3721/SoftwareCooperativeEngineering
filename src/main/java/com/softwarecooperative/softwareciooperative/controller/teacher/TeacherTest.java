package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.annotation.permission.SpecificRoleOnly;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/12-15:01:44
 */
@RestController
@RequestMapping("/teacher")
@Tag(name = "教师测试接口")
@Slf4j
public class TeacherTest {


    @SpecificRoleOnly(role="aaa")
    @GetMapping("test")
    @Operation(summary = "测试接口1")
    public AjaxResult test(){
        log.info("userId={}", BaseContext.getCurrentId());
        return AjaxResult.success();
    }
}
