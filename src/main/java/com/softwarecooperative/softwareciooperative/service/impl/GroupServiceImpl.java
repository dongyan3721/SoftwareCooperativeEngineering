package com.softwarecooperative.softwareciooperative.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.softwarecooperative.softwareciooperative.dao.mapper.GroupAppealLeaderMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.GroupMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.StudentMapper;
import com.softwarecooperative.softwareciooperative.framework.exception.service.GroupException;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroup;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroupAppealLeader;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealLeaderVO;
import com.softwarecooperative.softwareciooperative.service.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private StudentMapper studentMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public List<BGroup> getGroupByClass(Integer classId) {
        return groupMapper.selectByCond(BGroup.builder().classId(classId).build());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = {GroupException.class})
    public void approveLeader(Integer appealId, Boolean isAccept) {
        if (isAccept) {
            // 删除申请条目
            BGroupAppealLeader appealInfo = groupAppealLeaderMapper.selectById(appealId);
            if (appealInfo == null)
                throw new GroupException(StringConstant.NO_APPEAL);
            groupAppealLeaderMapper.delete(appealId);

            // 检查学生是否已经加入团队
            Integer studentId = appealInfo.getStudentId();
            BStudent bStudent = studentMapper.selectOne(BStudent.builder().studentId(studentId).build());
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

            // TODO 向组长通知组长申请已通过
        } else {
            // TODO 向组长通知组长申请未通过
            groupAppealLeaderMapper.delete(appealId);
        }
    }

    @Override
    public PageResult<AppealLeaderVO> getAppealLeaderByClass(Integer page, Integer pageSize, Integer classId) {
        PageHelper.startPage(page, pageSize);
        Page<AppealLeaderVO> res = groupAppealLeaderMapper.selectAppealLeaderVOByClass(classId);
        return new PageResult<>(res.getTotal(), res.getResult());
    }
}