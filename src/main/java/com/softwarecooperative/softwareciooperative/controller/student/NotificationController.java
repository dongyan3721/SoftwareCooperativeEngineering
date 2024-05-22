package com.softwarecooperative.softwareciooperative.controller.student;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.framework.net.TableDataInfo;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import com.softwarecooperative.softwareciooperative.pojo.vo.NotificationVO;
import com.softwarecooperative.softwareciooperative.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-11:45:05
 */
@RestController("StudentNotificationController")
@RequestMapping("/student/notice")
@Tag(name = "学生通知相关接口")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/confirm")
    @Operation(summary = "确认收到消息")
    public AjaxResult confirmNotification(Integer noticeId) {
        notificationService.confirm(noticeId, BClass.STUDENT);
        return AjaxResult.success();
    }

    @PostMapping("/allRead")
    @Operation(summary = "设置为全部已读")
    public AjaxResult allRead() {
        notificationService.allRead(BClass.STUDENT);
        return AjaxResult.success();
    }

    @GetMapping("")
    @Operation(summary = "学生端分页查询所有通知")
    public TableDataInfo pageSelect(Integer page, Integer pageSize) {
        PageResult<NotificationVO> res = notificationService.pageSelect(page, pageSize, BClass.STUDENT);
        return TableDataInfo.success(res.getRecords(), res.getTotal());
    }
}
