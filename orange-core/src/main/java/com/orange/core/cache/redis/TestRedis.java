package com.orange.core.cache.redis;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;

import java.util.Set;

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
        JedisManager jedis = new JedisManager();
        jedis.setJedisPool(pool);
        String dd = "abc";
        String cc = "你好";
        try {
            jedis.saveValueByKey(1, dd.getBytes(), cc.getBytes("UTF-8"), 10000);
            String v = new String(jedis.getValueByKey(1,dd.getBytes()),"UTF-8");
            System.out.println(v);
            jedis.saveValueByKey(1, "abced".getBytes(), "erer".getBytes("UTF-8"), 10000);

            Set<byte[]> set = jedis.keys(1,"ab*".getBytes());
            for(byte[] b : set){
                String temp = new String(b,"UTF-8");
                System.out.println(temp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
