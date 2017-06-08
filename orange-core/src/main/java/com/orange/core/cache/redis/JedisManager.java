package com.orange.core.cache.redis;

import com.orange.core.shiro.cache.JedisCache;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by chzq on 2017/5/24.
 */

public class JedisManager {

    Logger logger = Logger.getLogger(JedisManager.class);

    private JedisPool jedisPool;

    // 默认数据库
    public final static int DB_INDEX = 1;


    public Jedis getJedis() {
        Jedis jedis;
        try {
            jedis = getJedisPool().getResource();
        } catch (JedisConnectionException e) {
            throw new JedisConnectionException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jedis;
    }

    public Jedis getJedis(int dbIndex) {
        Jedis jedis;
        try {
            jedis = getJedisPool().getResource();
            jedis.select(dbIndex);
        } catch (JedisConnectionException e) {
            throw new JedisConnectionException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jedis;
    }

    public void returnResource(Jedis jedis) {
        if (jedis == null) {
            return;
        }
        jedis.close();
    }

    public byte[] getValueByKey(int dbIndex, byte[] key) {
        Jedis jedis = null;
        byte[] result = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            result = jedis.get(key);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    public void deleteByKey(int dbIndex, byte[] key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            Long result = jedis.del(key);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.set(key, value);
            if (expireTime > 0)
                jedis.expire(key, expireTime);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public Set<byte[]>  keys(int dbIndex, byte[] prefix){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            return jedis.keys(prefix);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public Set<byte[]>  values(int dbIndex, byte[] prefix){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            Set<byte[]> keys = keys(dbIndex,prefix);
            Set<byte[]>  values = new HashSet <byte[]>(keys.size());
            for (byte[] key : keys) {
                byte[] value = getValueByKey(dbIndex,key);
                if (value != null) {
                    values.add(value);
                }
            }
            return values;
        } catch (RuntimeException e) {
            throw e;
        } finally {
            returnResource(jedis);
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
