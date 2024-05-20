package com.softwarecooperative.softwareciooperative.pojo.dto;

import lombok.*;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/20-14:10:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TeacherLoginDTO {
    private Integer teacherId;
    private String password;
}
