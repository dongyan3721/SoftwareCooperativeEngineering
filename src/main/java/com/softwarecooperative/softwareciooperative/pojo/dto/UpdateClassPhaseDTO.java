package com.softwarecooperative.softwareciooperative.pojo.dto;

import lombok.*;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/23-16:16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateClassPhaseDTO {
    private Integer classId;
    private Integer phase;
}
