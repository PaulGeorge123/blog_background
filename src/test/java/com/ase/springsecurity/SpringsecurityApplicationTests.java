package com.ase.springsecurity;

import com.ase.springsecurity.entity.User;
import com.ase.springsecurity.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SpringsecurityApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("tom2");
        user.setPassword("1234566896");
        redisUtil.set("tom2",user);
    }

}
