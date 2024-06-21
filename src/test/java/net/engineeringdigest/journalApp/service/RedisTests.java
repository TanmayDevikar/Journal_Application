package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void testRedis() {
        redisTemplate.opsForValue().set("email", "tanmaydevikar99@gmail.com");
        Object email = redisTemplate.opsForValue().get("name");
        int a = 1;   //Just for debugging, statement is of no use

        /*Here, as we are setting email over here, and we try to get email on redis cli, we will get (nil), coz there is no sync between Spring Boot Redis and redis cli
          By setting the properties of Redis in application.yml; we have only configured Redis template, but it doesn't know which should be the serializer (which sets data) and which should be the deserializer(which gets data).
          The default serializer and deserializer are working in our case that is not the same for redis cli and Spring Boot redis


          To solve this issue, we need to make the serializer and the deserializer same for both Spring Boot and redis cli
          We are defining the serializer and deserializer in config--> RedisConfig.java*/
    }
}
