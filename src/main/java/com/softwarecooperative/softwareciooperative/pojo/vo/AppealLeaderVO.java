package com.softwarecooperative.softwareciooperative.pojo.vo;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/16-15:54:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AppealLeaderVO {
    private Integer appealId;
    private Integer studentId;
    private String groupName;
    private String studentName;
    private String studentAvatar;
    private LocalDateTime appealDate;
}
