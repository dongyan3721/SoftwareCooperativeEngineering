package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.StudentMapper;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.IllegalOperationException;
import com.softwarecooperative.softwareciooperative.framework.exception.service.ModifyPasswordException;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.dto.ChangePasswordDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.service.StudentService;
import com.softwarecooperative.softwareciooperative.utils.crypto.MD5Util;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public BStudent getStudentById(@NotNull Integer studentId) {
        return studentMapper.selectOne(BStudent.builder().studentId(studentId).build());
    }

    @Override
    public void modifyStudent(BStudent student) {
        Integer id = Integer.parseInt(BaseContext.getCurrentId());
        if(!id.equals(student.getStudentId()))
            throw new IllegalOperationException(StringConstant.ILLEGAL_OPERATION);
        studentMapper.update(student);
    }

    @Override
    public void modifyPswStudent(ChangePasswordDTO changePasswordDTO) {
        Integer id = Integer.parseInt(BaseContext.getCurrentId());
        BStudent student = studentMapper.selectOne(BStudent.builder().studentId(id).build());
        String oldPswMD5 = MD5Util.encrypt(changePasswordDTO.getOldPassword());
        if(!oldPswMD5.equals(student.getPassword()))
            throw new ModifyPasswordException(StringConstant.MODIFY_PASSWORD_FAILED);

        BStudent newStudent = BStudent.builder()
                .studentId(id)
                .password(MD5Util.encrypt(changePasswordDTO.getNewPassword()))
                .build();

        studentMapper.update(newStudent);
    }
}
