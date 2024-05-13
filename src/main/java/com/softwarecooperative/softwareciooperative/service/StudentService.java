package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.pojo.dto.ChangePasswordDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;

/**
 * @Description
 * @Author PinG
 * @Date 2024/5/13-20:48:31
 */
public interface StudentService {
    BStudent getStudentById(Integer studentId);
    void modifyStudent(BStudent student);
    void modifyPswStudent(ChangePasswordDTO changePasswordDTO);
}
