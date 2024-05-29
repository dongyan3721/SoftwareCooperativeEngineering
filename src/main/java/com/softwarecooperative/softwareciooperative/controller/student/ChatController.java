package com.softwarecooperative.softwareciooperative.controller.student;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.ChatRecord;
import com.softwarecooperative.softwareciooperative.service.ChatRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/26-21:01:17
 */
@RestController("StudentChatController")
@RequestMapping("/common/chat")
@Tag(name = "学生聊天相关接口")
@Slf4j
public class ChatController {

    @Autowired
    private ChatRecordService chatRecordService;

    @PostMapping("")
    @Operation(summary = "发送聊天消息")
    public AjaxResult sendChatMessage(@RequestParam(required = true) String content,
                                      @RequestParam(required = true) Integer contentType) {
        chatRecordService.studentAddOne(content, contentType);
        return AjaxResult.success();
    }
}
