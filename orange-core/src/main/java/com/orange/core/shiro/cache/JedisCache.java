package com.orange.core.shiro.cache;

import com.orange.common.utils.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;

/**
 * Created by chzq on 2017/5/25.
 */
public class JedisCache<K, V> implements Cache<K, V> {

    private JedisManager jedisManager;

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
    private static final String REDIS_SHIRO_CACHE = "shiro-cache:";

    public V get(K k) throws CacheException {
        byte[] byteKey = SerializeUtil.serialize(buildCacheKey(k));
        byte[] byteValue = new byte[0];
        try {
            byteValue = jedisManager.getValueByKey(DB_INDEX, byteKey);
        } catch (Exception e) {
        }
        return (V) SerializeUtil.deserialize(byteValue);
    }

    public V put(K k, V v) throws CacheException {
        V previos = get(k);
        try {
            jedisManager.saveValueByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(k)),
                    SerializeUtil.serialize(v), -1);
        } catch (Exception e) {
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
        jedisManager.getJedis().flushDB();
    }

    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    public Set <K> keys() {
        return null;
    }

    public Collection <V> values() {
        return null;
    }

    private String buildCacheKey(Object key) {
        return REDIS_SHIRO_CACHE  + ":" + key;
    }

}
