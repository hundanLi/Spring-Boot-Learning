package com.tcl.nosql.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@Slf4j
@SpringBootTest
class RedisTemplateTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void test() {
        log.info(redisTemplate.toString());
        redisTemplate.opsForValue().set("key", "value");
        Object key = redisTemplate.opsForValue().get("key");
        Assertions.assertEquals(key, "value");

    }
}