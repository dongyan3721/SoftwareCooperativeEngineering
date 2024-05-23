package com.softwarecooperative.softwareciooperative.pojo.dto;

import lombok.*;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/23-21:13:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MarkPerformanceDTO {
    private Integer targetStuId;
    private Integer groupId;
    private Integer taskId;
    private Integer performance;
    private String comment;
}
