package com.orange.core.shiro;

import com.orange.common.utils.SerializeUtil;
import com.orange.core.cache.redis.JedisManager;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by chzq on 2017/5/17.
 */
public class ShiroSessionDAO extends AbstractSessionDAO{

    Logger logger = Logger.getLogger(ShiroSessionDAO.class);

    private JedisManager jedisManager;

    /**
     * 为了不和其他的缓存混淆，采用追加前缀方式以作区分
     */
    private static final String REDIS_SHIRO_CACHE = "shiro_redis_session:";

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    private void saveSession(Session session) throws UnknownSessionException{
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        byte[] key = getByteKey(session.getId());
        byte[] value = SerializeUtil.serialize(session);
        // session 里面存的毫秒，jedis存的是秒
        Long  sessionTimeOut = session.getTimeout() / 1000;
        this.jedisManager.saveValueByKey(JedisManager.DB_INDEX,key, value,sessionTimeOut.intValue());
    }

    protected Session doReadSession(Serializable serializable) {
        if(serializable == null){
            logger.error("session id is null");
            return null;
        }
        byte[] seessionByte = jedisManager.getValueByKey(JedisManager.DB_INDEX,this.getByteKey(serializable));
        Session s = (Session)SerializeUtil.deserialize(seessionByte);
        return s;
    }

    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    public void delete(Session session) {
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        jedisManager.deleteByKey(JedisManager.DB_INDEX,getByteKey(session.getId()));
    }

    //用来统计当前活动的session
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        String prefix = this.REDIS_SHIRO_CACHE + "*";
        Set<byte[]> keys = jedisManager.keys(JedisManager.DB_INDEX,prefix.getBytes());
        if(keys != null && keys.size()>0){
            for(byte[] key:keys){
                Session s = (Session)SerializeUtil.deserialize(jedisManager.getValueByKey(JedisManager.DB_INDEX,key));
                sessions.add(s);
            }
        }
        return sessions;
    }

    /**
     * 获得byte[]型的key
     * @param sessionId
     * @return
     */
    private byte[] getByteKey(Serializable sessionId){
        String preKey = this.REDIS_SHIRO_CACHE + sessionId;
        return preKey.getBytes();
    }
}
