package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.StudentMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.TeacherMapper;
import com.softwarecooperative.softwareciooperative.framework.exception.service.LoginFailedException;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;
import com.softwarecooperative.softwareciooperative.service.LoginService;
import com.softwarecooperative.softwareciooperative.utils.crypto.MD5Util;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/28-23:25:49
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public BTeacher teacherLogin(@NotNull BTeacher teacher) throws LoginFailedException {
        teacher.setPassword(MD5Util.encrypt(teacher.getPassword()));
        log.info("MD5: {}", teacher.getPassword());
        BTeacher user = teacherMapper.selectOne(teacher);
        if (user == null)
            throw new LoginFailedException(StringConstant.LOGIN_FAILED);
        user.setPassword(null);
        return user;
    }

    @Override
    public BStudent studentLogin(@NotNull BStudent student) throws LoginFailedException {
        student.setPassword(MD5Util.encrypt(student.getPassword()));
        log.info("MD5: {}", student.getPassword());
        BStudent user = studentMapper.selectOne(student);
        if (user == null)
            throw new LoginFailedException(StringConstant.LOGIN_FAILED);
        user.setPassword(null);
        return user;
    }
}
