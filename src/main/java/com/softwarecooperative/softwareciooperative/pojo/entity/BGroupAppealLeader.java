package com.softwarecooperative.softwareciooperative.pojo.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/15-21:42:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BGroupAppealLeader implements Serializable {
    private Integer appealId;
    private Integer studentId;
    private String groupName;
    private Integer classId;
    private String groupAvatar;
    private String groupIntroduction;
    private LocalDateTime appealDate;
}
