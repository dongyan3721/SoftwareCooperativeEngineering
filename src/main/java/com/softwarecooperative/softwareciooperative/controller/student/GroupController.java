package com.softwarecooperative.softwareciooperative.controller.student;

import com.softwarecooperative.softwareciooperative.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/15-21:37:46
 */
@RestController("StudentGroupController")
@RequestMapping("/student/group")
@Tag(name = "学生团队相关接口")
@Slf4j
public class GroupController {

    @Autowired
    private GroupService groupService;

}
