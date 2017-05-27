package com.orange.core.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;

/**
 * Created by chzq on 2017/5/25.
 */
public class JedisCache<K, V> implements Cache<K, V> {

    @Autowired
    private JedisManager jedisManager;

    public V get(K k) throws CacheException {
        return null;
    }

    public V put(K k, V v) throws CacheException {
        return null;
    }

    public V remove(K k) throws CacheException {
        return null;
    }

    public void clear() throws CacheException {

    }

    public int size() {
        return 0;
    }

    public Set <K> keys() {
        return null;
    }

    public Collection <V> values() {
        return null;
    }
}
