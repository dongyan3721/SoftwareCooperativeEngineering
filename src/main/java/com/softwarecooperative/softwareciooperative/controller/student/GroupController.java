package com.softwarecooperative.softwareciooperative.controller.student;

import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.framework.net.TableDataInfo;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroup;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroupAppealLeader;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealInVO;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealLeaderVO;
import com.softwarecooperative.softwareciooperative.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/appealLeader")
    @Operation(summary = "申请成为组长")
    public AjaxResult appealLeader(@RequestBody BGroupAppealLeader bGroupAppealLeader) throws IOException {
        groupService.appealLeader(bGroupAppealLeader);
        return AjaxResult.success();
    }

    @DeleteMapping("/{groupId}")
    @Operation(summary = "解散团队")
    public AjaxResult disbandGroup(@PathVariable Integer groupId) throws IOException {
        groupService.disbandGroup(groupId);
        return AjaxResult.success();
    }

    @PutMapping("")
    @Operation(summary = "修改团队信息")
    public AjaxResult editGroupInfo(@RequestBody BGroup bGroup) {
        groupService.editGroupInfo(bGroup);
        return AjaxResult.success();
    }


    @GetMapping("/{classId}")
    @Operation(summary = "学生端获取团队列表")
    public AjaxResult listAllGroup(@PathVariable Integer classId) {
        List<BGroup> res = groupService.getGroupByClass(classId);
        return AjaxResult.success(res);
    }

    @PostMapping("/appealIn")
    @Operation(summary = "申请加入团队")
    public AjaxResult appealIn(Integer groupId) throws IOException {
        groupService.appealIn(groupId);
        return AjaxResult.success();
    }

    @PostMapping("/approveIn")
    @Operation(summary = "批准加入团队")
    public AjaxResult approveIn(Integer appealId, Boolean isAccept) throws IOException {
        groupService.approveIn(appealId, isAccept);
        return AjaxResult.success();
    }

    @PostMapping("/appoint")
    @Operation(summary = "指派成员角色")
    public AjaxResult appoint(Integer studentId, Integer role) throws IOException {
        groupService.appoint(studentId, role);
        return AjaxResult.success();
    }


    @GetMapping("/appeal/{groupId}")
    @Operation(summary = "分页查询团队所有加入申请")
    public TableDataInfo pageGetAppealIn(@PathVariable Integer groupId, Integer page, Integer pageSize) {
        PageResult<AppealInVO> res = groupService.pageGetAppealIn(groupId, page, pageSize);
        return TableDataInfo.success(res.getRecords(), res.getTotal());
    }

}
