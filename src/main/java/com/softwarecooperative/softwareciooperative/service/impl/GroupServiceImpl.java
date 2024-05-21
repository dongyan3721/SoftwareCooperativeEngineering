package com.softwarecooperative.softwareciooperative.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.softwarecooperative.softwareciooperative.dao.mapper.*;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.GroupException;
import com.softwarecooperative.softwareciooperative.framework.net.NotificationTemplate;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.*;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealInVO;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealLeaderVO;
import com.softwarecooperative.softwareciooperative.service.GroupService;
import com.softwarecooperative.softwareciooperative.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/16-15:01:56
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupAppealLeaderMapper groupAppealLeaderMapper;

    @Autowired
    private GroupAppealInMapper groupAppealInMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<BGroup> getGroupByClass(Integer classId) {
        return groupMapper.selectByCond(BGroup.builder().classId(classId).build());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = {GroupException.class})
    public void approveLeader(Integer appealId, Boolean isAccept) throws IOException {
        BGroupAppealLeader appealInfo = groupAppealLeaderMapper.selectById(appealId);
        if (appealInfo == null)
            throw new GroupException(StringConstant.NO_APPEAL);
        Integer studentId = appealInfo.getStudentId();
        BStudent bStudent = studentMapper.selectOne(BStudent.builder().studentId(studentId).build());

        if (isAccept) {
            // 删除申请条目
            groupAppealLeaderMapper.delete(appealId);

            // 检查学生是否已经加入团队
            if (bStudent.getStudentGroup() != null || !BStudent.NO_ROLE.equals(bStudent.getStudentRole()))
                throw new GroupException(StringConstant.STUDENT_ALREADY_IN_GROUP);

            // 添加团队
            // 组建团队实体
            BGroup group = BGroup.builder()
                    .groupLeaderId(bStudent.getStudentId())
                    .groupLeaderName(bStudent.getStudentName())
                    .groupLeaderAvatar(bStudent.getAvatar())
                    .build();
            BeanUtils.copyProperties(appealInfo, group);
            groupMapper.insert(group);
            // 修改学生角色
            studentMapper.update(BStudent.builder()
                    .studentId(studentId)
                    .studentRole(BStudent.DEVELOPMENT_MANAGER)
                    .studentGroup(group.getGroupId())
                    .build());

            // 向组长通知组长申请已通过
            notificationService.sendNotifToOneStudent(BClass.SYSTEM, studentId,
                    NotificationTemplate.LEADER_APPROVE(bStudent.getStudentName(), group.getGroupName()));
        } else {
            groupAppealLeaderMapper.delete(appealId);
            // 向学生通知组长申请未通过
            notificationService.sendNotifToOneStudent(BClass.SYSTEM, studentId,
                    NotificationTemplate.LEADER_DENY(bStudent.getStudentName()));
        }
    }

    @Override
    public PageResult<AppealLeaderVO> getAppealLeaderByClass(Integer page, Integer pageSize, Integer classId) {
        PageHelper.startPage(page, pageSize);
        Page<AppealLeaderVO> res = groupAppealLeaderMapper.selectAppealLeaderVOByClass(classId);
        return new PageResult<>(res.getTotal(), res.getResult());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = GroupException.class)
    public void appealLeader(BGroupAppealLeader bGroupAppealLeader) throws IOException {
        // 检查学生是否已进入团队
        Integer studentId = Integer.parseInt(BaseContext.getCurrentId());
        BStudent curStudent = studentMapper.selectOne(BStudent.builder().studentId(studentId).build());
        if (curStudent.getStudentGroup() != null)
            throw new GroupException(StringConstant.STUDENT_ALREADY_IN_GROUP);

        // 组装申请条目，写入数据库表
        bGroupAppealLeader.setStudentId(studentId);
        bGroupAppealLeader.setAppealDate(LocalDateTime.now());
        groupAppealLeaderMapper.insert(bGroupAppealLeader);

        // 通知对应教师
        BClass curClass = classMapper.selectOne(BClass.builder().classId(curStudent.getStudentClass()).build());
        notificationService.sendNotifToOneTeacher(
                BClass.SYSTEM,
                curClass.getTeacherId(),
                NotificationTemplate.NEW_LEADER_APPEAL(curStudent.getStudentName())
        );

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = GroupException.class)
    public void disbandGroup(Integer groupId) throws IOException {
        // 检查是否有id为groupId的团队
        BGroup group = groupMapper.selectOne(BGroup.builder().groupId(groupId).build());
        if (group == null)
            throw new GroupException(StringConstant.NO_GROUP);

        // 向组内学生通知
        BStudent query = BStudent.builder().studentGroup(groupId).build();
        List<BStudent> bStudents = studentMapper.selectByCond(query);
        List<Integer> ids = bStudents.stream().map(BStudent::getStudentId).toList();
        notificationService.sendNotifToStudents(BClass.SYSTEM, ids, NotificationTemplate.GROUP_DISBAND(group.getGroupName()));

        // 修改所有id为groupId的学生
        studentMapper.exitGroup(groupId);
        // 删除id为groupId的团队
        groupMapper.delete(groupId);
    }

    @Override
    public void editGroupInfo(BGroup bGroup) {
        groupMapper.update(bGroup);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = GroupException.class)
    public void appealIn(Integer groupId) throws IOException {
        Integer curId = Integer.parseInt(BaseContext.getCurrentId());
        // 检查是否已加入其他团队
        BStudent stu = studentMapper.selectOne(BStudent.createIdQuery(curId));
        if (stu.getStudentGroup() != null)
            throw new GroupException(StringConstant.STUDENT_ALREADY_IN_GROUP);
        // 检查是否已有组长申请
        BGroupAppealLeader appealQuery = BGroupAppealLeader.builder().studentId(curId).build();
        BGroupAppealLeader appeal = groupAppealLeaderMapper.selectOne(appealQuery);
        if (appeal != null)
            throw new GroupException(StringConstant.STUDENT_ALREADY_APPEAL_LEADER);

        // 添加申请
        groupAppealInMapper.insert(BGroupAppealIn.builder()
                .groupId(groupId)
                .studentId(curId)
                .appealDate(LocalDateTime.now())
                .build());

        // 向组长通知
        BStudent leader = groupMapper.selectLeaderByGroupId(groupId);
        notificationService.sendNotifToOneStudent(
                BClass.SYSTEM,
                leader.getStudentId(),
                NotificationTemplate.NEW_MEMBER_APPEAL(curId.toString())
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = GroupException.class)
    public void approveIn(Integer appealId, Boolean isAccept) throws IOException {
        // 获取申请
        BGroupAppealIn appealInfo = groupAppealInMapper.selectOne(BGroupAppealIn.createIdQuery(appealId));
        if (appealInfo == null)
            throw new GroupException(StringConstant.NO_APPEAL);
        Integer studentId = appealInfo.getStudentId();
        Integer groupId = appealInfo.getGroupId();
        BStudent bStudent = studentMapper.selectOne(BStudent.createIdQuery(studentId));

        // 删除申请条目
        groupAppealInMapper.delete(appealId);
        if (isAccept) {
            // 检查学生是否已经加入团队
            if (bStudent.getStudentGroup() != null)
                throw new GroupException(StringConstant.STUDENT_ALREADY_IN_GROUP);

            // 修改学生角色
            BStudent newStudent = BStudent.builder()
                    .studentId(studentId)
                    .studentGroup(groupId)
                    .build();
            studentMapper.update(newStudent);

            BGroup group = groupMapper.selectOne(BGroup.createIdQuery(groupId));
            // 向组长通知加入申请已通过
            notificationService.sendNotifToOneStudent(
                    BClass.SYSTEM,
                    studentId,
                    NotificationTemplate.IN_APPROVE(bStudent.getStudentName(), group.getGroupName())
            );

        } else {
            // 向学生通知加入申请未通过
            notificationService.sendNotifToOneStudent(
                    BClass.SYSTEM,
                    studentId,
                    NotificationTemplate.IN_DENY(bStudent.getStudentName())
            );
        }
    }

    @Override
    public void appoint(Integer studentId, Integer role) throws IOException {
        // 检查studentId和组长id是否相等
        Integer leaderId = Integer.parseInt(BaseContext.getCurrentId());
        if (studentId.equals(leaderId))
            throw new GroupException(StringConstant.LEADER_ROLE_CANT_BE_CHANGED);
        // 检查目标学生是否是本组组员
        BStudent leader = studentMapper.selectOne(BStudent.createIdQuery(leaderId));
        BStudent targetStudent = studentMapper.selectOne(BStudent.createIdQuery(studentId));
        if (targetStudent.getStudentGroup() == null || !targetStudent.getStudentGroup().equals(leader.getStudentGroup()))
            throw new GroupException(StringConstant.STUDENT_NOT_IN_GROUP);

        // 修改成员角色
        studentMapper.update(BStudent.builder()
                .studentId(targetStudent.getStudentId())
                .studentRole(role.toString())
                .build());

        // 通知目标学生已更改角色
        notificationService.sendNotifToOneStudent(
                BClass.SYSTEM,
                studentId,
                NotificationTemplate.ROLE_CHANGED(targetStudent.getStudentName(), role)
        );
    }

    @Override
    public PageResult<AppealInVO> pageGetAppealIn(Integer groupId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<AppealInVO> res = groupAppealInMapper.selectAppealInVOByGroupId(groupId);
        return new PageResult<>(res.getTotal(), res.getResult());
    }

    @Override
    public List<BStudent> getAllMemberInGroup(Integer groupId) {
        BStudent query = BStudent.builder()
                .studentGroup(groupId)
                .build();
        return studentMapper.selectByCond(query);
    }
}
