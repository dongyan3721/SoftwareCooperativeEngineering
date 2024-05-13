package com.softwarecooperative.softwareciooperative.pojo.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/13-20:46:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChangePasswordDTO implements Serializable {
    private String oldPassword;
    private String newPassword;
}
