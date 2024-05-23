package com.softwarecooperative.softwareciooperative.service.impl;

import com.softwarecooperative.softwareciooperative.dao.mapper.*;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.GroupException;
import com.softwarecooperative.softwareciooperative.framework.exception.service.TaskException;
import com.softwarecooperative.softwareciooperative.framework.net.NotificationTemplate;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.*;
import com.softwarecooperative.softwareciooperative.service.NotificationService;
import com.softwarecooperative.softwareciooperative.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/22-10:36:12
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ClassTaskMapper classTaskMapper;

    @Autowired
    private StudentTaskSubmitMapper studentTaskSubmitMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<BClassTask> getClassAllTask(Integer classId) {
        BClassTask query = BClassTask.builder()
                .classId(classId)
                .build();
        return classTaskMapper.selectByCond(query);
    }

    @Override
    public List<BStudentTaskSubmit> getGroupAllTask(Integer groupId, Integer taskId) {
        BStudentTaskSubmit query = BStudentTaskSubmit.builder()
                .taskHandlerGroupId(groupId)
                .taskId(taskId)
                .build();
        return studentTaskSubmitMapper.selectByCond(query);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = TaskException.class)
    public void submitSubTask(Integer recordId, String submitLink) throws IOException {
        // 判断是否是任务接收者
        BStudentTaskSubmit subTask = studentTaskSubmitMapper.selectOne(BStudentTaskSubmit.createIdQuery(recordId));
        Integer curId = Integer.parseInt(BaseContext.getCurrentId());
        if (!curId.equals(subTask.getTaskHandler()))
            throw new TaskException(StringConstant.NOT_TASK_HANDLER);
        // 判断任务是否过期，每个任务的ddl延长一段时间
        BClassTask task = classTaskMapper.selectOne(BClassTask.createIdQuery(subTask.getTaskId()));
        if (LocalDateTime.now().isAfter(task.getDeadline().plusSeconds(BClassTask.DEADLINE_BUFFER_SECONDS)))
            throw new TaskException(StringConstant.TASK_EXPIRED);
        // 判断提交状态是否为待提交状态
        if (!BStudentTaskSubmit.WAIT_FOR_STUDENT_HAND_ON.toString().equals(subTask.getSubmitStatus()))
            throw new TaskException(StringConstant.TASK_STATUS_ERROR);

        // 修改submitLink, submitTime, submitStatus
        BStudentTaskSubmit newSubmit = BStudentTaskSubmit.builder()
                .recordId(recordId)
                .submitLink(submitLink)
                .submitTime(LocalDateTime.now())
                .submitStatus(BStudentTaskSubmit.WAIT_FOR_TEACHER_MARK.toString())
                .build();
        studentTaskSubmitMapper.update(newSubmit);

        // 通知老师
        BStudent curStu = studentMapper.selectOne(BStudent.createIdQuery(curId));
        BClass curClass = classMapper.selectOne(BClass.createIdQuery(curStu.getStudentClass()));
        if (task.getTaskStudentRole().equals(curStu.getStudentRole())) {
            notificationService.sendNotifToOneTeacher(
                    BClass.SYSTEM,
                    curClass.getTeacherId(),
                    NotificationTemplate.TASK_HANDED_ON(curStu.getStudentName(), task.getTaskStudentRole())
            );
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = TaskException.class)
    public void updateSubTask(Integer recordId, String submitLink) {
        // 判断是否是任务接收者
        BStudentTaskSubmit subTask = studentTaskSubmitMapper.selectOne(BStudentTaskSubmit.createIdQuery(recordId));
        Integer curId = Integer.parseInt(BaseContext.getCurrentId());
        if (!curId.equals(subTask.getTaskHandler()))
            throw new TaskException(StringConstant.NOT_TASK_HANDLER);
        // 判断任务是否过期，每个任务的ddl延长一段时间
        BClassTask task = classTaskMapper.selectOne(BClassTask.createIdQuery(subTask.getTaskId()));
        if (LocalDateTime.now().isAfter(task.getDeadline().plusSeconds(BClassTask.DEADLINE_BUFFER_SECONDS)))
            throw new TaskException(StringConstant.TASK_EXPIRED);
        // 判断提交状态是否为待提交状态
        if (!BStudentTaskSubmit.WAIT_FOR_TEACHER_MARK.toString().equals(subTask.getSubmitStatus()))
            throw new TaskException(StringConstant.TASK_STATUS_ERROR);

        // 修改submitLink, submitTime, submitStatus
        BStudentTaskSubmit newSubmit = BStudentTaskSubmit.builder()
                .recordId(recordId)
                .submitLink(submitLink)
                .submitTime(LocalDateTime.now())
                .submitStatus(BStudentTaskSubmit.WAIT_FOR_TEACHER_MARK.toString())
                .build();
        studentTaskSubmitMapper.update(newSubmit);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = TaskException.class)
    public void updateSubTaskDescription(Integer recordId, String description) throws IOException {
        // 判断是否是当前阶段经理
        Integer curId = Integer.parseInt(BaseContext.getCurrentId());
        BStudent curStu = studentMapper.selectOne(BStudent.createIdQuery(curId));
        BStudentTaskSubmit subtask = studentTaskSubmitMapper.selectOne(BStudentTaskSubmit.createIdQuery(recordId));
        BClassTask task = classTaskMapper.selectOne(BClassTask.createIdQuery(subtask.getTaskId()));
        if (!curStu.getStudentRole().equals(task.getTaskStudentRole()))
            throw new TaskException(StringConstant.ILLEGAL_OPERATION);

        // 修改description和任务状态
        BStudentTaskSubmit newSubtask = BStudentTaskSubmit.builder()
                .recordId(recordId)
                .taskHandlerWork(description)
                .submitStatus(BStudentTaskSubmit.WAIT_FOR_STUDENT_HAND_ON.toString())
                .build();
//        log.info(newSubtask.toString());
//        log.info("{}, {}", recordId, description);
        studentTaskSubmitMapper.update(newSubtask);

        // 通知组员完成任务，修改自己的任务描述无需发送提醒
        if (!curId.equals(subtask.getTaskHandler()))
            notificationService.sendNotifToOneStudent(
                    BClass.SYSTEM,
                    subtask.getTaskHandler(),
                    NotificationTemplate.SUBTASK_CHANGED(subtask.getTaskHandlerName())
            );
    }

    @Override
    public BStudentTaskSubmit getSubtaskSubmit(Integer recordId) {
        return studentTaskSubmitMapper.selectOne(BStudentTaskSubmit.createIdQuery(recordId));
    }

    @Override
    public BStudentTaskSubmit getMainTaskSubmit(Integer groupId, Integer taskId) {
        BStudentTaskSubmit query = BStudentTaskSubmit.builder()
                .taskHandlerGroupId(groupId)
                .taskId(taskId)
                .submitType(BStudentTaskSubmit.MAIN_TASK_SUBMIT)
                .build();
        return studentTaskSubmitMapper.selectOne(query);
    }
}