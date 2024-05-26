package com.softwarecooperative.softwareciooperative;

import com.softwarecooperative.softwareciooperative.pojo.entity.ChatRecord;
import com.softwarecooperative.softwareciooperative.service.ChatRecordService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/24-20:33:30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ChatRecordConcurrentTest {

    @Autowired
    private ChatRecordService chatRecordService;

    @Test
    public void concurrentTest() throws InterruptedException {
        log.info("开始进行ChatRecord业务高并发写入测试...");
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(concurrentThread);
            thread.setDaemon(true);
            thread.start();
            log.info("线程{}已启动", i);
        }
        Thread.sleep(10000);
    }

    Runnable concurrentThread = new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
            Random random = new Random();
            LocalDateTime deathTime = LocalDateTime.now().plusSeconds(5);
            while (LocalDateTime.now().isBefore(deathTime)) {
                chatRecordService.addOne(ChatRecord.builder()
                        .userId(142543169)
                        .content("嗨！想我了吗")
                        .contentType(ChatRecord.TEXT)
                        .userType(ChatRecord.STUDENT)
                        .groupId(144475584)
                        .sendDateTime(LocalDateTime.now())
                        .build());
                Thread.sleep(30);
//                Thread.sleep(random.nextInt(1) * 100);
            }
        }
    };
}
