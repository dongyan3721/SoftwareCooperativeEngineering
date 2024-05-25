package com.softwarecooperative.softwareciooperative.controller.student;

import com.softwarecooperative.softwareciooperative.framework.annotation.permission.SpecificRoleOnly;
import com.softwarecooperative.softwareciooperative.framework.net.AjaxResult;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.framework.net.TableDataInfo;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroup;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroupAppealLeader;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealInVO;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealLeaderVO;
import com.softwarecooperative.softwareciooperative.pojo.vo.GroupVO;
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
    @SpecificRoleOnly(role = BStudent.DEVELOPMENT_MANAGER)
    @Operation(summary = "解散团队")
    public AjaxResult disbandGroup(@PathVariable Integer groupId) throws IOException {
        groupService.disbandGroup(groupId);
        return AjaxResult.success();
    }

    @PutMapping("")
    @SpecificRoleOnly(role = BStudent.DEVELOPMENT_MANAGER)
    @Operation(summary = "修改团队信息")
    public AjaxResult editGroupInfo(@RequestBody BGroup bGroup) {
        groupService.editGroupInfo(bGroup);
        return AjaxResult.success();
    }


    @GetMapping("/{classId}")
    @Operation(summary = "学生端获取团队列表")
    public AjaxResult listAllGroup(@PathVariable Integer classId) {
        List<GroupVO> res = groupService.getGroupByClassWithHasAppeal(classId);
        return AjaxResult.success(res);
    }

    @PostMapping("/appealIn")
    @Operation(summary = "申请加入团队")
    public AjaxResult appealIn(Integer groupId) throws IOException {
        groupService.appealIn(groupId);
        return AjaxResult.success();
    }

    @PostMapping("/approveIn")
    @SpecificRoleOnly(role = BStudent.DEVELOPMENT_MANAGER)
    @Operation(summary = "批准加入团队")
    public AjaxResult approveIn(Integer appealId, Boolean isAccept) throws IOException {
        groupService.approveIn(appealId, isAccept);
        return AjaxResult.success();
    }

    @PostMapping("/appoint")
    @SpecificRoleOnly(role = BStudent.DEVELOPMENT_MANAGER)
    @Operation(summary = "指派成员角色")
    public AjaxResult appoint(Integer studentId, Integer role) throws IOException {
        groupService.appoint(studentId, role);
        return AjaxResult.success();
    }


    @GetMapping("/appeal/{groupId}")
    @SpecificRoleOnly(role = BStudent.DEVELOPMENT_MANAGER)
    @Operation(summary = "分页查询团队所有加入申请")
    public TableDataInfo pageGetAppealIn(@PathVariable Integer groupId, Integer page, Integer pageSize) {
        PageResult<AppealInVO> res = groupService.pageGetAppealIn(groupId, page, pageSize);
        return TableDataInfo.success(res.getRecords(), res.getTotal());
    }

    @GetMapping("/allMember")
    @Operation(summary = "查看团队内所有成员")
    public AjaxResult getAllMemberInGroup(Integer groupId) {
        List<BStudent> res = groupService.getAllMemberInGroup(groupId);
        return AjaxResult.success(res);
    }

    @PostMapping("/transferLeader")
    @SpecificRoleOnly(role = BStudent.DEVELOPMENT_MANAGER)
    @Operation(summary = "转让组长")
    public AjaxResult transferLeader(Integer studentId) throws IOException {
        groupService.transferLeader(studentId);
        return AjaxResult.success();
    }

    @DeleteMapping("/deleteMember")
    @Operation(summary = "删除团队成员")
    public AjaxResult deleteMember(Integer studentId) throws IOException {
        groupService.deleteMember(studentId);
        return AjaxResult.success();
    }

    @DeleteMapping("/exit")
    @Operation(summary = "退出团队")
    public AjaxResult exitGroup(Integer groupId) throws IOException {
        groupService.exitGroup(groupId);
        return AjaxResult.success();
    }

    @GetMapping("/getDetail")
    @Operation(summary = "根据id查询团队")
    public AjaxResult getGroupByGroupId(Integer groupId) {
        BGroup res = groupService.getGroupByGroupId(groupId);
        return AjaxResult.success(res);
    }

}
