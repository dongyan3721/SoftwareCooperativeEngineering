package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroup;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealLeaderVO;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/16-14:57:48
 */
public interface GroupService {

    /**
     * 获取
     * @param classId
     * @return
     */
    List<BGroup> getGroupByClass(Integer classId);

    void approveLeader(Integer appealId, Boolean isAccept);

    PageResult<AppealLeaderVO> getAppealLeaderByClass(Integer page, Integer pageSize, Integer classId);
}
