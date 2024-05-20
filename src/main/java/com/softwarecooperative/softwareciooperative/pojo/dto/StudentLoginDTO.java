package com.softwarecooperative.softwareciooperative.pojo.dto;

import lombok.*;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/20-14:09:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StudentLoginDTO {
    private Integer studentId;
    private String password;
}
