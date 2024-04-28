package com.softwarecooperative.softwareciooperative;

import com.softwarecooperative.softwareciooperative.dao.repository.ChatRecordRepo;
import com.softwarecooperative.softwareciooperative.pojo.entity.ChatRecord;
import com.softwarecooperative.softwareciooperative.service.ChatRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/27-19:39:20
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MongoDBTest {

    @Autowired
    private ChatRecordRepo chatRecordRepo;

    @Autowired
    private ChatRecordService chatRecordService;

    @Test
    public void insert() {
        chatRecordRepo.insert(ChatRecord.builder()
                .userId(110)
                .content("保护海洋")
                .contentType(ChatRecord.TEXT)
                .userType(ChatRecord.STUDENT)
                .groupId(1919810)
                .sendDateTime(LocalDateTime.now())
                .build());
        chatRecordRepo.insert(ChatRecord.builder()
                .userId(110)
                .content("保护海洋！")
                .contentType(ChatRecord.TEXT)
                .userType(ChatRecord.STUDENT)
                .groupId(1919810)
                .sendDateTime(LocalDateTime.now())
                .build());
        chatRecordRepo.insert(ChatRecord.builder()
                .userId(120)
                .content("保护个牛魔酬宾")
                .contentType(ChatRecord.TEXT)
                .userType(ChatRecord.STUDENT)
                .groupId(1919810)
                .sendDateTime(LocalDateTime.now())
                .build());
        chatRecordRepo.insert(ChatRecord.builder()
                .userId(110)
                .content("复活冥火大公")
                .contentType(ChatRecord.TEXT)
                .userType(ChatRecord.STUDENT)
                .groupId(1919810)
                .sendDateTime(LocalDateTime.now())
                .build());
        chatRecordRepo.insert(ChatRecord.builder()
                .userId(110)
                .content("复活流萤")
                .contentType(ChatRecord.TEXT)
                .userType(ChatRecord.STUDENT)
                .groupId(1919810)
                .sendDateTime(LocalDateTime.now())
                .build());
        chatRecordRepo.insert(ChatRecord.builder()
                .userId(110)
                .content("不好看，在电影院睡了三小时")
                .contentType(ChatRecord.TEXT)
                .userType(ChatRecord.STUDENT)
                .groupId(1919810)
                .sendDateTime(LocalDateTime.now())
                .build());
    }

    @Test
    public void pageSelect() {
        chatRecordService.getOnePage(1919810, 4, 3).forEach(r -> System.out.println(r));
        System.out.println("--------------------------------------------------");
        chatRecordService.getOnePage(1919810, 1, 3).forEach(r -> System.out.println(r));
        System.out.println("--------------------------------------------------");
        chatRecordService.getOnePage(1919810, 0, 3).forEach(r -> System.out.println(r));
    }
}
