package com.softwarecooperative.softwareciooperative.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.softwarecooperative.softwareciooperative.dao.mapper.ClassChapterSettingsMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.StudentMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.TeacherMapper;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.IllegalOperationException;
import com.softwarecooperative.softwareciooperative.framework.exception.service.ModifyPasswordException;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.dto.ChangePasswordDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassChapterSettings;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
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

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClassChapterSettingsMapper classChapterSettingsMapper;

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

    @Override
    public void addOneStudent(BStudent student) {
        //学生姓名，学生班级，头像链接
        student.setStudentRole(BStudent.NO_ROLE);
        student.setPassword(MD5Util.encrypt(StringConstant.DEFAULT_PASSWORD));
        student.setStudentGroup(null);
        studentMapper.insertOne(student);
    }

    @Override
    public void deleteOneStudent(Integer studentId) {
        studentMapper.deleteOne(studentId);
    }

    @Override
    public void modifyStudent(BStudent student) {
        if(student.getPassword()!=null){
            student.setPassword(MD5Util.encrypt(student.getPassword()));
        }
        studentMapper.update(student);
    }

    @Override
    public PageResult<BStudent> pageSelect(Integer page, Integer pageSize, Integer classId) {
        PageHelper.startPage(page,pageSize);
        BStudent query = BStudent.builder()
                .studentClass(classId)
                .build();
        Page<BStudent> res = studentMapper.pageSelect(query);
        return new PageResult<>(res.getTotal(), res.getResult());
    }
}
