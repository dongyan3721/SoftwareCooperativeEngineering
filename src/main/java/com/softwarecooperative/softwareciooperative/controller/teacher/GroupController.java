package com.softwarecooperative.softwareciooperative.controller.teacher;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.framework.net.TableDataInfo;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroup;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealLeaderVO;
import com.softwarecooperative.softwareciooperative.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/15-21:37:46
 */
@RestController("TeacherGroupController")
@RequestMapping("/teacher/group")
@Tag(name = "教师团队相关接口")
@Slf4j
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/{classId}")
    @Operation(summary = "列出教学班所有组")
    public AjaxResult listAllGroup(@PathVariable Integer classId) {
        List<BGroup> res = groupService.getGroupByClass(classId);
        return AjaxResult.success(res);
    }

    @GetMapping("/appeal/{classId}")
    @Operation(summary = "分页列出当前教学班所有组长申请")
    public TableDataInfo listAppealLeader(Integer page, Integer pageSize, @PathVariable Integer classId) {
        PageResult<AppealLeaderVO> res = groupService.getAppealLeaderByClass(page, pageSize, classId);
        return TableDataInfo.success(res.getRecords(), res.getTotal());
    }

    @PostMapping("/approveLeader")
    @Operation(summary = "教师批准学生成为组长")
    public AjaxResult approveLeader(Integer appealId, Boolean isAccept) {
        groupService.approveLeader(appealId, isAccept);
        return AjaxResult.success();
    }
}
