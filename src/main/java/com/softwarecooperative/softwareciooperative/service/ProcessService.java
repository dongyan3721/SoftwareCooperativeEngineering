package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.pojo.vo.GroupAndPhaseVO;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-19:24:27
 */
public interface ProcessService {

    Integer getGroupPhase(Integer groupId);

    List<GroupAndPhaseVO> getClassGroupPhases(Integer classId);
}
