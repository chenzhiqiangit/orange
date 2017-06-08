package com.orange.core.shiro.cache;

import com.orange.core.cache.redis.JedisManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

/**
 * Created by chzq on 2017/5/17.
 */
public class ShiroCacheManager  implements CacheManager, Destroyable {

    private JedisManager jedisManager;


    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

    public <K, V> Cache <K, V> getCache(String s) throws CacheException {
        return new JedisCache(s,jedisManager);
    }

    public void destroy() throws Exception {

    }
}
