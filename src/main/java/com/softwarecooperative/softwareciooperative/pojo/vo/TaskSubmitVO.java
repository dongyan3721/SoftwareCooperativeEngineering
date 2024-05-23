package com.softwarecooperative.softwareciooperative.pojo.vo;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/23-19:34:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TaskSubmitVO {
    private Integer recordId;
    private Integer taskId;
    private Integer taskHandler;
    private Integer taskHandlerGroupId;
    private String taskHandlerName;
    private String taskHandlerWork;
    private String submitLink;
    private String submitStatus;
    private LocalDateTime submitTime;
    private Integer submitType;
    private Integer performance;
    private Integer comment;
}
