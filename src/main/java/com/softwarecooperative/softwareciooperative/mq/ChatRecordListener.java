package com.softwarecooperative.softwareciooperative.mq;

import com.alibaba.fastjson2.JSONObject;
import com.softwarecooperative.softwareciooperative.dao.repository.ChatRecordRepo;
import com.softwarecooperative.softwareciooperative.pojo.entity.ChatRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/24-20:01:10
 */
@Component
@Slf4j
public class ChatRecordListener {

    @Autowired
    private ChatRecordRepo chatRecordRepo;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final List<ChatRecord> buffer = new Vector<>();

    public ChatRecordListener() {
        scheduler.scheduleAtFixedRate(this::saveMessages, 1, 1, TimeUnit.SECONDS);
    }

    @RabbitListener(queues = "chatRecord.queue")
    public void receiveMessage(String message) {
        ChatRecord record = JSONObject.parseObject(message, ChatRecord.class);
        buffer.add(record);
    }

    private void saveMessages() {
        List<ChatRecord> messagesToSave;
        int cnt = 0;
        synchronized (buffer) {
            if (buffer.isEmpty()) {
//                log.info("没有消息处理");
                return;
            }

            messagesToSave = new Vector<>(buffer);
            cnt = buffer.size();
            buffer.clear();
        }
        chatRecordRepo.insert(messagesToSave);
        log.info("成功插入{}条消息", cnt);
    }
}
