package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.ClassMapper;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import com.softwarecooperative.softwareciooperative.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-11:28:14
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public List<BClass> getTeacherClass() {
        Integer teacherId = Integer.parseInt(BaseContext.getCurrentId());
        return classMapper.selectByCond(BClass.builder().teacherId(teacherId).build());
    }

    @Override
    public void updateClassPhase(Integer classId, Integer phase) {
        BClass newBClass = BClass.builder()
                .classId(classId)
                .phase(phase)
                .build();
        classMapper.update(newBClass);
    }
}
