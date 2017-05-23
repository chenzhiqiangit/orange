package com.orange.core.cache.redis;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by chzq on 2017/5/23.
 */
public class TestRedis {
    public static void main(String[] args){
        String[] locations = { "spring-redis.xml" };
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                locations);
        context.start();
        JedisPool pool = (JedisPool)context.getBean("jedisPool");
        Jedis jedis = pool.getResource();
        jedis.set("key3","中国");
        String key3 = jedis.get("key3");
        System.out.println(key3);
    }
}
