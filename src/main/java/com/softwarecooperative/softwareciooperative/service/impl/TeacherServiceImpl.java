package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.TeacherMapper;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.IllegalOperationException;
import com.softwarecooperative.softwareciooperative.framework.exception.service.ModifyPasswordException;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.dto.ChangePasswordDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;
import com.softwarecooperative.softwareciooperative.service.TeacherService;
import com.softwarecooperative.softwareciooperative.utils.crypto.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/13-20:04:59
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public BTeacher getTeacherById(Integer id) {
        return teacherMapper.selectOne(BTeacher.builder().teacherId(id).build());
    }

    @Override
    public void modifyTeacher(BTeacher bTeacher) {
        Integer id = Integer.parseInt(BaseContext.getCurrentId());
        if (!id.equals(bTeacher.getTeacherId()))
            throw new IllegalOperationException(StringConstant.ILLEGAL_OPERATION);

        teacherMapper.update(bTeacher);
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        Integer teacherId = Integer.parseInt(BaseContext.getCurrentId());
        BTeacher teacher = teacherMapper.selectOne(BTeacher.builder().teacherId(teacherId).build());

        String oldPasswordMD5 = MD5Util.encrypt(changePasswordDTO.getOldPassword());
        if (!oldPasswordMD5.equals(teacher.getPassword()))
            throw new ModifyPasswordException(StringConstant.MODIFY_PASSWORD_FAILED);

        BTeacher newTeacher = BTeacher.builder()
                        .teacherId(teacherId)
                        .password(MD5Util.encrypt(changePasswordDTO.getNewPassword()))
                        .build();

        teacherMapper.update(newTeacher);
    }
}
