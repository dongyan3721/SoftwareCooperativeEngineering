package com.softwarecooperative.softwareciooperative;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        String name = "TLE.in.exchange";
        Message message = MessageBuilder
                .withBody("114514".getBytes(StandardCharsets.UTF_8))
                .setExpiration("10000")
                .build();

        rabbitTemplate.convertAndSend(name, "114514", message);
    }
}
