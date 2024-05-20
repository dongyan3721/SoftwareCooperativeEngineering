package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroup;
import com.softwarecooperative.softwareciooperative.pojo.entity.BGroupAppealLeader;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealInVO;
import com.softwarecooperative.softwareciooperative.pojo.vo.AppealLeaderVO;

import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/16-14:57:48
 */
public interface GroupService {

    List<BGroup> getGroupByClass(Integer classId);

    void approveLeader(Integer appealId, Boolean isAccept) throws IOException;

    PageResult<AppealLeaderVO> getAppealLeaderByClass(Integer page, Integer pageSize, Integer classId);

    void appealLeader(BGroupAppealLeader bGroupAppealLeader) throws IOException;

    void disbandGroup(Integer groupId) throws IOException;

    void editGroupInfo(BGroup bGroup);

    void appealIn(Integer groupId) throws IOException;

    void approveIn(Integer appealId, Boolean isAccept) throws IOException;

    void appoint(Integer studentId, Integer role) throws IOException;

    PageResult<AppealInVO> pageGetAppealIn(Integer groupId, Integer page, Integer pageSize);

    List<BStudent> getAllMemberInGroup(Integer groupId);
}
