package com.softwarecooperative.softwareciooperative.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/21-21:08:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupVO {
    private Integer groupId;
    private String groupName;
    private Integer classId;
    private String groupAvatar;
    private String groupIntroduction;
    private Integer groupLeaderId;
    private String groupLeaderName;
    private String groupLeaderAvatar;
    private Boolean hasAppeal;
}
