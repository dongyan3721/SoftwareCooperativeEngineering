package com.softwarecooperative.softwareciooperative.service.impl;

import com.alibaba.fastjson2.JSON;
import com.softwarecooperative.softwareciooperative.dao.mapper.*;
import com.softwarecooperative.softwareciooperative.framework.annotation.ClearAllCache;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.GroupException;
import com.softwarecooperative.softwareciooperative.framework.exception.service.TaskException;
import com.softwarecooperative.softwareciooperative.framework.net.NotificationTemplate;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.dto.MarkPerformanceDTO;
import com.softwarecooperative.softwareciooperative.pojo.entity.*;
import com.softwarecooperative.softwareciooperative.service.NotificationService;
import com.softwarecooperative.softwareciooperative.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private StudentPerformanceStudentMapper studentPerformanceStudentMapper;

    @Autowired
    private StudentPerformanceTeacherMapper studentPerformanceTeacherMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Cacheable(cacheNames = "classTaskCache", key = "#classId")
    public List<BClassTask> getClassAllTask(Integer classId) {
        BClassTask query = BClassTask.builder()
                .classId(classId)
                .build();
        return classTaskMapper.selectByCond(query);
    }

    @Override
    @Cacheable(cacheNames = "groupAllTask", key = "#groupId + '_' + #taskId")
    public List<BStudentTaskSubmit> getGroupAllTask(Integer groupId, Integer taskId) {
        BStudentTaskSubmit query = BStudentTaskSubmit.builder()
                .taskHandlerGroupId(groupId)
                .taskId(taskId)
                .build();
        return studentTaskSubmitMapper.selectByCond(query);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @ClearAllCache({"groupAllTask", "groupProcess"})
    @CacheEvict(cacheNames = "subtaskCache", key = "#recordId")
    public void submitSubTask(Integer recordId, String submitLink) throws IOException {
        // TODO 这里需要判断是否是主任务，如果是主任务需要在阶段任务全部提交后再提交
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
    @Transactional(propagation = Propagation.REQUIRED)
    @ClearAllCache({"groupAllTask"})
    @CacheEvict(cacheNames = "subtaskCache", key = "#recordId")
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
    @Transactional(propagation = Propagation.REQUIRED)
    @ClearAllCache({"groupAllTask"})
    @CacheEvict(cacheNames = "subtaskCache", key = "#recordId")
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
    @Cacheable(cacheNames = "subtaskCache", key = "#recordId")
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void markPerformanceByStudent(MarkPerformanceDTO mark) throws IOException {
        // 判断目标组员的任务是否已提交
        BStudentTaskSubmit query = BStudentTaskSubmit.builder()
                .taskHandler(mark.getTargetStuId())
                .taskId(mark.getTaskId())
                .taskHandlerGroupId(mark.getGroupId())
                .build();
        BStudentTaskSubmit submit = studentTaskSubmitMapper.selectOne(query);
        if (submit == null)  // 未找到提交
            throw new TaskException(StringConstant.TASK_NOT_EXIST);
        if (BStudentTaskSubmit.WAIT_FOR_STUDENT_HAND_ON.toString().equals(submit.getSubmitStatus()) ||
            BStudentTaskSubmit.UNASSIGNED.toString().equals(submit.getSubmitStatus()))  // 提交状态处于未提交或未配置
            throw new TaskException(StringConstant.TASK_STATUS_ERROR);

        // 打分
        BStudent targetStu = studentMapper.selectOne(BStudent.createIdQuery(mark.getTargetStuId()));
        Integer curStuId = Integer.parseInt(BaseContext.getCurrentId());

        BStudentPerformanceStudent markEntity = BStudentPerformanceStudent.builder()
                .performance(mark.getPerformance())
                .comment(mark.getComment())
                .performanceClass(targetStu.getStudentClass())
                .performanceReceptor(mark.getTargetStuId())
                .performanceMaker(curStuId)
                .performanceStage(mark.getTaskId())
                .build();
        studentPerformanceStudentMapper.insert(markEntity);

        // 修改对应分数状态
        BStudentTaskSubmit newSubmit = BStudentTaskSubmit.builder()
                .submitStatus(BStudentTaskSubmit.PASS.toString())
                .recordId(submit.getRecordId())
                .build();
        studentTaskSubmitMapper.update(newSubmit);

        // 通知被打分组员
        notificationService.sendNotifToOneStudent(
                BClass.SYSTEM,
                mark.getTargetStuId(),
                NotificationTemplate.STUDENT_HAS_MARKED(targetStu.getStudentName(), mark.getPerformance())
        );


    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = "groupProcess", key = "#mark.groupId")
    public void markPerformanceByTeacher(MarkPerformanceDTO mark) throws IOException {
        // 判断目标组员的任务是否已提交
        BStudentTaskSubmit query = BStudentTaskSubmit.builder()
                .taskHandler(mark.getTargetStuId())
                .taskId(mark.getTaskId())
                .taskHandlerGroupId(mark.getGroupId())
                .build();
        BStudentTaskSubmit submit = studentTaskSubmitMapper.selectOne(query);
        if (submit == null)  // 未找到提交
            throw new TaskException(StringConstant.TASK_NOT_EXIST);
        if (BStudentTaskSubmit.WAIT_FOR_STUDENT_HAND_ON.toString().equals(submit.getSubmitStatus()) ||
                BStudentTaskSubmit.UNASSIGNED.toString().equals(submit.getSubmitStatus()))  // 提交状态处于未提交或未配置
            throw new TaskException(StringConstant.TASK_STATUS_ERROR);

        // 打分
        BStudent targetStu = studentMapper.selectOne(BStudent.createIdQuery(mark.getTargetStuId()));
        Integer curTeacherId = Integer.parseInt(BaseContext.getCurrentId());

        BStudentPerformanceTeacher markEntity = BStudentPerformanceTeacher.builder()
                .performance(mark.getPerformance())
                .comment(mark.getComment())
                .performanceClass(targetStu.getStudentClass())
                .performanceReceptor(mark.getTargetStuId())
                .performanceMaker(curTeacherId)
                .performanceStage(mark.getTaskId())
                .build();
        studentPerformanceTeacherMapper.insert(markEntity);

        // 修改对应分数状态
        BStudentTaskSubmit newSubmit = BStudentTaskSubmit.builder()
                .submitStatus(BStudentTaskSubmit.PASS.toString())
                .recordId(submit.getRecordId())
                .build();
        studentTaskSubmitMapper.update(newSubmit);

        // 通知被所有组员
        List<Integer> stuIds = studentMapper.selectIdByCond(BStudent.builder().studentGroup(mark.getGroupId()).build());
        notificationService.sendNotifToStudents(
                BClass.SYSTEM,
                stuIds,
                NotificationTemplate.TEACHER_HAS_MARKED(mark.getPerformance())
        );
    }

    @Override
    @CacheEvict(cacheNames = "classTaskCache", key = "#task.classId")
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTask(BClassTask task) {
        BClassTask oldTask = classTaskMapper.selectOne(BClassTask.createIdQuery(task.getTaskId()));
        classTaskMapper.update(task);
        if (task.getDeadline().toEpochSecond(ZoneOffset.of("+8")) ==
                oldTask.getDeadline().toEpochSecond(ZoneOffset.of("+8")))
            return;
        long ddl = task.getDeadline().toInstant(ZoneOffset.of("+8")).toEpochMilli();

        // 将定时任务插入消息队列
        Message message = MessageBuilder
                .withBody(JSON.toJSONString(task).getBytes(StandardCharsets.UTF_8))
                .setExpiration(String.valueOf(Math.max(ddl - System.currentTimeMillis() - 24 * 60 * 60 * 1000, 0)))
                .build();
        rabbitTemplate.convertAndSend("TLE.in.exchange", "114514", message);
    }
}
