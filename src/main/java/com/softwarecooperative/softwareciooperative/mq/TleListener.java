package com.softwarecooperative.softwareciooperative.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TleListener {

    @RabbitListener(queues = "TLE.out.queue")
    public void TLEListener(String message) {
        // TODO 处理超时消息，一般消息以json格式发送和接收
        log.info(message);
    }
}
