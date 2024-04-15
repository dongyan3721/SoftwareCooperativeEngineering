package com.softwarecooperative.softwareciooperative;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate() {
        System.out.println(redisTemplate);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        System.out.println(valueOperations.get("city"));
    }

    @Test
    public void testString() {
        // SET/SETEX SETNX GET
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("city", "下北泽");
        String city = (String) valueOperations.get("city");
        System.out.println(city);
        valueOperations.set("code", "114514", 1L, TimeUnit.MINUTES);
        System.out.println(valueOperations.get("code"));
        valueOperations.setIfAbsent("lock", "1");
        System.out.println(valueOperations.get("lock"));
        valueOperations.setIfAbsent("lock", "2");
        System.out.println(valueOperations.get("lock"));
    }
}
