package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.TeacherMapper;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.IllegalOperationException;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.BTeacher;
import com.softwarecooperative.softwareciooperative.service.TeacherService;
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
}
