package com.orange.core.shiro.cache;

import com.orange.common.utils.SerializeUtil;
import com.orange.core.cache.redis.JedisManager;
import com.orange.core.shiro.ShiroSessionDAO;
import org.apache.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.*;

/**
 * Created by chzq on 2017/5/25.
 */
public class JedisCache<K, V> implements Cache<K, V> {

    Logger logger = Logger.getLogger(JedisCache.class);

    private String name;

    private JedisManager jedisManager;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }


    /**
     * Redis 分片(分区)，也可以在配置文件中配置
     */
    private static final int DB_INDEX = 1;

    /**
     * 为了不和其他的缓存混淆，采用追加前缀方式以作区分
     */
    private static final String REDIS_SHIRO_CACHE = "shiro_redis_cache:";

    public JedisCache(String name,JedisManager jedisManager){
        this.name= name;
        this.jedisManager = jedisManager;
    }

    public V get(K k) throws CacheException {
        byte[] byteKey = SerializeUtil.serialize(buildCacheKey(k));
        byte[] byteValue = new byte[0];
        try {
            byteValue = jedisManager.getValueByKey(DB_INDEX, byteKey);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return (V) SerializeUtil.deserialize(byteValue);
    }

    public V put(K k, V v) throws CacheException {
        V previos = get(k);
        try {
            jedisManager.saveValueByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(k)),
                    SerializeUtil.serialize(v), -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return previos;
    }

    public V remove(K k) throws CacheException {
        V previos = get(k);
        try {
            jedisManager.deleteByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(k)));
        } catch (Exception e) {
        }
        return previos;
    }

    public void clear() throws CacheException {
        jedisManager.getJedis(DB_INDEX).flushDB();
    }

    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    public Set <K> keys() {
        try {
            Set <byte[]> keys = jedisManager.keys(DB_INDEX, REDIS_SHIRO_CACHE.getBytes());
            Set <K> newKeys = new HashSet <K>(keys.size());
            for (byte[] key : keys) {
                newKeys.add((K) key);
            }
            return newKeys;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Collections.EMPTY_SET;
    }

    public Collection <V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<V>(keys.size());
        for (K key : keys){
            values.add(get(key));
        }
        return values;
    }

    private String buildCacheKey(Object key) {
        return REDIS_SHIRO_CACHE   + getName() + ":" + key;
    }
}
