package com.softwarecooperative.softwareciooperative.service;

import com.softwarecooperative.softwareciooperative.framework.exception.service.LoginFailedException;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:25:01
 */
public interface LoginService {
    BTeacher teacherLogin(BTeacher teacher) throws LoginFailedException;

    BStudent studentLogin(BStudent student) throws LoginFailedException;
}
