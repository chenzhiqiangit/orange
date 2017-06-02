package com.orange.core.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * Created by chzq on 2017/5/17.
 */
public class ShiroCacheManager  extends AbstractCacheManager {
    protected Cache createCache(String s) throws CacheException {
        return null;
    }

}
