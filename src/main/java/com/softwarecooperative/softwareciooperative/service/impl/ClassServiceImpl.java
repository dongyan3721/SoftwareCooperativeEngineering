package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.*;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.ClassException;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.*;
import com.softwarecooperative.softwareciooperative.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClassTaskMapper classTaskMapper;

    @Override
    public List<BClass> getTeacherClass() {
        Integer teacherId = Integer.parseInt(BaseContext.getCurrentId());
        return classMapper.selectByCond(BClass.builder().teacherId(teacherId).build());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateClassPhase(Integer classId, Integer phase) {
        BClass clazz = classMapper.selectOne(BClass.createIdQuery(classId));
        if (phase - clazz.getPhase() != 1)
            throw new ClassException(StringConstant.SET_PHASE_FAILED);

        if (BClass.INTEGRATING.equals(phase)) {
            // 初始化教学班任务
            initializeClassTasks(classId);
        } else if (BClass.PROCESSING.equals(phase)) {
            // TODO 进入项目进行阶段自动执行的操作
        } else if (BClass.FINISHED.equals(phase)) {
            // TODO 进入结课状态自动执行的操作
        } else {
            // 阶段码不正确
            throw new ClassException(StringConstant.ILLEGAL_PHASE);
        }

        BClass newBClass = BClass.builder()
                .classId(classId)
                .phase(phase)
                .build();
        classMapper.update(newBClass);
    }

    @Override
    public void newClass(BClass clazz) {
        int curId = Integer.parseInt(BaseContext.getCurrentId());
        BTeacher curTeacher = teacherMapper.selectOne(BTeacher.createIdQuery(curId));
        BCourse course = courseMapper.selectById(clazz.getCourseId());
        BClass newClass = BClass.builder()
                .teacherId(curId)
                .teacherName(curTeacher.getTeacherName())
                .courseId(clazz.getCourseId())
                .courseName(course.getCourseName())
                .classTerm(clazz.getClassTerm())
                .classPicture(clazz.getClassPicture())
                .phase(BClass.ADDING_STUDENTS)
                .build();
        classMapper.insert(newClass);
    }

    @Override
    public void deleteClass(Integer classId) {
        List<Integer> stuIds = studentMapper.selectIdByCond(BStudent.builder().studentClass(classId).build());
        if (!stuIds.isEmpty())
            throw new ClassException(StringConstant.CLASS_EXIST_STUDENT);

        classMapper.delete(classId);
    }

    @Override
    public void update(BClass clazz) {
        classMapper.update(clazz);
    }

    @Override
    public BClass getClassByClassId(Integer classId) {
        return classMapper.selectOne(BClass.createIdQuery(classId));
    }

    private void initializeClassTasks(Integer classId) {
        BClassTask task = BClassTask.builder()
                .classId(classId)
                .taskContent(StringConstant.DEMAND_ANALYSE)
                .deadline(LocalDateTime.now())
                .taskOrder(1)
                .taskStudentRole(BStudent.PRODUCT_MANAGER)
                .build();
        classTaskMapper.insert(task);
        task.setTaskContent(StringConstant.PROCESS_PLANNING);
        task.setTaskOrder(2);
        task.setTaskStudentRole(BStudent.PLANNING_MANAGER);
        classTaskMapper.insert(task);
        task.setTaskContent(StringConstant.QUALITY_PLANNING);
        task.setTaskOrder(3);
        task.setTaskStudentRole(BStudent.QUALITY_MANAGER);
        classTaskMapper.insert(task);
        task.setTaskContent(StringConstant.DEVELOPING);
        task.setTaskOrder(4);
        task.setTaskStudentRole(BStudent.DEVELOPMENT_MANAGER);
        classTaskMapper.insert(task);
        task.setTaskContent(StringConstant.TESTING);
        task.setTaskOrder(5);
        task.setTaskStudentRole(BStudent.TEST_MANAGER);
        classTaskMapper.insert(task);
    }
}
