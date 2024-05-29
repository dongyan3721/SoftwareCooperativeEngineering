package com.softwarecooperative.softwareciooperative.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.softwarecooperative.softwareciooperative.dao.mapper.*;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.net.NotificationTemplate;
import com.softwarecooperative.softwareciooperative.framework.net.PageResult;
import com.softwarecooperative.softwareciooperative.pojo.entity.*;
import com.softwarecooperative.softwareciooperative.pojo.vo.NotificationVO;
import com.softwarecooperative.softwareciooperative.service.NotificationService;
import com.softwarecooperative.softwareciooperative.ws.StudentNotificationWs;
import com.softwarecooperative.softwareciooperative.ws.TeacherNotificationWs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/17-10:30:55
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private NoticeStudentMapper noticeStudentMapper;

    @Autowired
    private NoticeReceiveStudentMapper noticeReceiveStudentMapper;

    @Autowired
    private NoticeTeacherMapper noticeTeacherMapper;

    @Autowired
    private NoticeReceiveTeacherMapper noticeReceiveTeacherMapper;

    @Override
    @CacheEvict(cacheNames = "notificationCache",
            key = "T(com.softwarecooperative.softwareciooperative.pojo.entity.BClass).TEACHER + '_' + T(com.softwarecooperative.softwareciooperative.framework.context.BaseContext).getCurrentId() + '*'")
    public void sendNotifToOneTeacher(Integer sourceRole, Integer teacherId, String message) throws IOException {
        Publisher publisher = getPublisher(sourceRole);
        if (publisher == null)
            return;

        // 组装消息
        BNoticeTeacher notice = BNoticeTeacher.builder()
                .noticePublisher(publisher.getName())
                .noticePublisherAvatar(publisher.getAvatar())
                .noticeContent(message)
                .noticePublishSnapshot(LocalDateTime.now())
                .build();
        noticeTeacherMapper.insert(notice);
        // 发送提醒
        noticeReceiveTeacherMapper.insertOne(
                BNoticeReceiveTeacher.builder()
                        .noticeId(notice.getNoticeId())
                        .noticeReceiverId(teacherId)
                        .noticeStatus(BNoticeReceiveStudent.NOT_READ.toString())
                        .build()
        );

        // 向前端发送提醒消息
        TeacherNotificationWs.sendToOneClient(teacherId, NotificationTemplate.NEW_NOTICE);
    }

    @Override
    @CacheEvict(cacheNames = "notificationCache",
            key = "T(com.softwarecooperative.softwareciooperative.pojo.entity.BClass).STUDENT + '_' + T(com.softwarecooperative.softwareciooperative.framework.context.BaseContext).getCurrentId() + '*'")
    public void sendNotifToOneStudent(Integer sourceRole, Integer studentId, String message) throws IOException {
        Publisher publisher = getPublisher(sourceRole);
        if (publisher == null)
            return;

        // 组装消息
        BNoticeStudent notice = BNoticeStudent.builder()
                .noticePublisher(publisher.getName())
                .noticePublisherAvatar(publisher.getAvatar())
                .noticeContent(message)
                .noticePublishSnapshot(LocalDateTime.now())
                .build();
        noticeStudentMapper.insert(notice);
        // 发送提醒
        noticeReceiveStudentMapper.insertOne(BNoticeReceiveStudent.builder()
                .noticeId(notice.getNoticeId())
                .noticeReceiverId(studentId)
                .noticeStatus(BNoticeReceiveStudent.NOT_READ.toString())
                .build()
        );

        // 向前端发送提醒消息
        StudentNotificationWs.sendToOneClient(studentId, NotificationTemplate.NEW_NOTICE);
    }

    @Override
    @CacheEvict(cacheNames = "notificationCache",
            key = "T(com.softwarecooperative.softwareciooperative.pojo.entity.BClass).STUDENT + '*'")
    public void sendNotifToStudents(Integer sourceRole, List<Integer> ids, String message) throws IOException {
        Publisher publisher = getPublisher(sourceRole);
        if (publisher == null)
            return;

        // 组装消息
        BNoticeStudent notice = BNoticeStudent.builder()
                .noticePublisher(publisher.getName())
                .noticePublisherAvatar(publisher.getAvatar())
                .noticeContent(message)
                .noticePublishSnapshot(LocalDateTime.now())
                .build();
        noticeStudentMapper.insert(notice);
        // 发送提醒
        List<BNoticeReceiveStudent> notices = new ArrayList<>();
        ids.forEach(id -> notices.add(BNoticeReceiveStudent.builder()
                .noticeId(notice.getNoticeId())
                .noticeReceiverId(id)
                .noticeStatus(BNoticeReceiveStudent.NOT_READ.toString())
                .build())
        );
        noticeReceiveStudentMapper.insertBatch(notices);

        // 向前端发送提醒消息
        for (Integer id : ids)
            StudentNotificationWs.sendToOneClient(id, NotificationTemplate.NEW_NOTICE);
    }

    @Override
    @CacheEvict(cacheNames = "notificationCache",
            key = "#role + '_' + T(com.softwarecooperative.softwareciooperative.framework.context.BaseContext).getCurrentId() + '*'")
    public void confirm(Integer noticeId, Integer role) {
        if (role.equals(BClass.TEACHER)) {
            BNoticeReceiveTeacher newEntity = BNoticeReceiveTeacher.builder()
                    .noticeId(noticeId)
                    .noticeStatus(BNoticeReceiveStudent.ALREADY_READ.toString())
                    .build();
            noticeReceiveTeacherMapper.update(newEntity);
        } else if (role.equals(BClass.STUDENT)) {
            BNoticeReceiveStudent newEntity = BNoticeReceiveStudent.builder()
                    .noticeId(noticeId)
                    .noticeStatus(BNoticeReceiveStudent.ALREADY_READ.toString())
                    .build();
            noticeReceiveStudentMapper.update(newEntity);
        }
    }

    @Override
    @CacheEvict(cacheNames = "notificationCache",
            key = "#role + '_' + T(com.softwarecooperative.softwareciooperative.framework.context.BaseContext).getCurrentId() + '*'")
    public void allRead(Integer role) {
        Integer id = Integer.parseInt(BaseContext.getCurrentId());
        if (role.equals(BClass.TEACHER)) {
            BNoticeReceiveTeacher newEntity = BNoticeReceiveTeacher.builder()
                    .noticeReceiverId(id)
                    .noticeStatus(BNoticeReceiveStudent.ALREADY_READ.toString())
                    .build();
            noticeReceiveTeacherMapper.update(newEntity);
        } else if (role.equals(BClass.STUDENT)) {
            BNoticeReceiveStudent newEntity = BNoticeReceiveStudent.builder()
                    .noticeReceiverId(id)
                    .noticeStatus(BNoticeReceiveStudent.ALREADY_READ.toString())
                    .build();
            noticeReceiveStudentMapper.update(newEntity);
        }
    }

    @Override
    @Cacheable(cacheNames = "notificationCache",
            key = "#role + '_' + T(com.softwarecooperative.softwareciooperative.framework.context.BaseContext).getCurrentId() + '_' + #page + '_' + #pageSize")
    public PageResult<NotificationVO> pageSelect(Integer page, Integer pageSize, Integer role) {
        Integer id = Integer.parseInt(BaseContext.getCurrentId());
        PageHelper.startPage(page, pageSize);
        if (role.equals(BClass.TEACHER)) {
            Page<NotificationVO> res = noticeTeacherMapper.pageSelect(id);
            return new PageResult<>(res.getTotal(), res.getResult());
        } else if (role.equals(BClass.STUDENT)) {
            Page<NotificationVO> res = noticeStudentMapper.pageSelect(id);
            return new PageResult<>(res.getTotal(), res.getResult());
        }
        return null;
    }

    private Publisher getPublisher(Integer sourceRole) {
        String strId = BaseContext.getCurrentId();
        Integer id = Integer.parseInt(strId == null ? "0" : strId);
        Publisher publisher = new Publisher();
        switch (sourceRole) {
            case BClass.STUDENT:
                BStudent student = studentMapper.selectOne(BStudent.builder().studentId(id).build());
                publisher.setName(student.getStudentName());
                publisher.setAvatar(student.getAvatar());
                break;
            case BClass.TEACHER:
                BTeacher teacher = teacherMapper.selectOne(BTeacher.builder().teacherId(id).build());
                publisher.setName(teacher.getTeacherName());
                publisher.setAvatar(teacher.getAvatar());
                break;
            case BClass.SYSTEM:
                BTeacher system = teacherMapper.selectOne(BTeacher.builder().teacherId(0).build());
                publisher.setName(system.getTeacherName());
                publisher.setAvatar(system.getAvatar());
                break;
            default:
                return null;
        }
        return publisher;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Publisher {
        private String name;
        private String avatar;
    }
}
