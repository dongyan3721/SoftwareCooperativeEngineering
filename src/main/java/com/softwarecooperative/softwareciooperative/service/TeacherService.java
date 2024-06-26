package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.pojo.dto.ChangePasswordDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterSettings;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/13-20:03:39
 */
public interface TeacherService {
    BTeacher getTeacherById(Integer id);

    void modifyTeacher(BTeacher bTeacher);

    void changePassword(ChangePasswordDTO changePasswordDTO);

    void addOneStudent(BStudent student);

    void deleteOneStudent(Integer studentId);

    void modifyStudent(BStudent student);

    PageResult<BStudent> pageSelect(Integer page, Integer pageSize, Integer classId, String studentName, Integer studentId);
}
