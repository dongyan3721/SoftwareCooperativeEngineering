package com.softwarecooperative.softwareciooperative.framework.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfiguration {

    // 使用Spring AMQP进行声明
    @Bean // 原交换机
    public DirectExchange oriExchange() {
        return new DirectExchange("TLE.in.exchange", true, false, new HashMap<>());
    }

    @Bean // 死信交换机
    public DirectExchange dlExchange() {
        return new DirectExchange("TLE.out.exchange", true, false, new HashMap<>());
    }

    @Bean // 原队列
    public Queue oriQueue() {
        Map<String, Object> map = new HashMap<>();
        // 设置死信交换机，当前队列中的消息变成死信后进入死信交换机
        map.put("x-dead-letter-exchange", "TLE.out.exchange");

        return new Queue("TLE.in.queue", true, false, false, map);
    }

    @Bean // 死信队列
    public Queue dlQueue() {
        return new Queue("TLE.out.queue", true, false, false, new HashMap<>());
    }

    @Bean // 绑定原队列到原交换机
    public Binding bindingOriEx() {
        return BindingBuilder.bind(oriQueue()).to(oriExchange()).with("114514");
    }

    @Bean // 绑定死信队列到死信交换机
    public Binding bindingDlx() {
        return BindingBuilder.bind(dlQueue()).to(dlExchange()).with("114514");
    }

}
