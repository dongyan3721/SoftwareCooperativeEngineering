package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.GroupMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.StudentMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.StudentTaskSubmitMapper;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroup;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.vo.GroupAndPhaseVO;
import com.softwarecooperative.softwareciooperative.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-19:24:46
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private StudentTaskSubmitMapper studentTaskSubmitMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Integer getGroupPhase(Integer groupId) {
        return studentTaskSubmitMapper.selectGroupPhaseByGroupId(groupId);
    }

    @Override
    public List<GroupAndPhaseVO> getClassGroupPhases(Integer classId) {
        List<GroupAndPhaseVO> res = new ArrayList<>();
        groupMapper.selectIdByCond(BGroup.builder().classId(classId).build())
                .forEach(gId -> {
                    res.add(GroupAndPhaseVO.builder()
                            .groupId(gId)
                            .process(getGroupPhase(gId))
                            .build());
                });
        return res;
    }
}
