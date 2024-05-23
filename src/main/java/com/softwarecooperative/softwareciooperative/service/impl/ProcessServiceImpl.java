package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.StudentTaskSubmitMapper;
import com.softwarecooperative.softwareciooperative.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-19:24:46
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private StudentTaskSubmitMapper studentTaskSubmitMapper;

    @Override
    public Integer getGroupPhase(Integer groupId) {
        return studentTaskSubmitMapper.selectGroupPhaseByGroupId(groupId);
    }
}
