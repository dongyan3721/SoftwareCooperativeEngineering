package com.softwarecooperative.softwareciooperative.mq;

import com.alibaba.fastjson2.JSON;
import com.softwarecooperative.softwareciooperative.dao.mapper.ClassTaskMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.StudentMapper;
import com.softwarecooperative.softwareciooperative.framework.net.NotificationTemplate;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClassTask;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import com.softwarecooperative.softwareciooperative.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class TleListener {

    @Autowired
    private ClassTaskMapper classTaskMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "TLE.out.queue")
    public void TLEListener(String message) throws IOException {
        // 处理超时消息，一般消息以json格式发送和接收
        log.info(message);
        BClassTask oldTask = JSON.parseObject(message, BClassTask.class);
        BClassTask curTask = classTaskMapper.selectOne(BClassTask.createIdQuery(oldTask.getTaskId()));
        if (!LocalDateTime.now().isAfter(curTask.getDeadline()))
            return;

        // 提醒业务
        List<Integer> stuInClass = studentMapper.selectIdByCond(BStudent.builder().studentClass(curTask.getClassId()).build());
        notificationService.sendNotifToStudents(
                BClass.SYSTEM,
                stuInClass,
                NotificationTemplate.TASK_REMAIN_24_HOURS(curTask.getTaskContent())
        );
    }
}
