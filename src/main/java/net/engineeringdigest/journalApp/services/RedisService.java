package net.engineeringdigest.journalApp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T>T get(String key, Class<T> entityClass) {
        try {
            //T represents the generic type class
            Object o = redisTemplate.opsForValue().get(key);
            //Above, we are fetching key from redis, which is in Object type. So we need to convert it into a POJO class, which is implemented below
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass);
        } catch(Exception e) {
            log.error("Exception ", e);
            return null;
        }

    }

    public void set(String key, Object o, long ttl) {
        //Ttl is the time after which the stored cache in redis will expire. If we want to store the temperature for 5 minutes, then ttl will be 300 as time unit is in seconds.
        try {

            //redisTemplate.opsForValue().set(key, o, ttl, TimeUnit.SECONDS);
            //In RedisConfig, we have configured that for serializer, both keys and values will be in String, so it will expect string to set in Redis and the above statement will not work as we are passing object.
            //So, we can convert the object into String (which will be the JSON values) using ObjectMapper.
            //For example, the JSON values of the object will be stored in String format as "{\"current\":{\"feelslike_c\":37.2}}"

            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(o);


            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);  //TimeUnit.SECONDS is the time unit for ttl
        } catch(Exception e) {
            log.error("Exception ", e);
        }

    }
}
