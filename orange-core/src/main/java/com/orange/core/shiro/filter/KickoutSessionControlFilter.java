package com.orange.core.shiro.filter;

import com.orange.common.utils.SerializeUtil;
import com.orange.core.cache.redis.JedisManager;
import com.orange.entity.UserBo;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by chzq on 2017/5/16.
 */
public class KickoutSessionControlFilter  extends AccessControlFilter {

    private static final Logger logger = Logger.getLogger(ShiroFilterUtils.class);
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    private SessionManager sessionManager;

    private JedisManager jedisManager;

    private String kickoutUrl;

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()) {
            return true;
        }

        Session session = subject.getSession();

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            subject.logout();
            saveRequest(request);
            WebUtils.issueRedirect(request, response, "login");
            logger.info("被踢出了....................");
            return false;
        }

        UserBo  user = (UserBo) subject.getPrincipal();
        Serializable sessionId = session.getId();
        LinkedList<Serializable> userList;
        byte[] userlistByte = jedisManager.getValueByKey(JedisManager.DB_INDEX,user.getUserName().getBytes());
        if(userlistByte !=null && userlistByte.length > 0) {
            userList = (LinkedList) SerializeUtil.deserialize(userlistByte);
        }else{
            userList = new LinkedList<Serializable>();
        }
        if(!userList.contains(sessionId) && session.getAttribute("kickout") == null){
            userList.add(sessionId);
            byte[] value = SerializeUtil.serialize(userList);
            jedisManager.saveValueByKey(JedisManager.DB_INDEX,user.getUserName().getBytes(),value,0);
        }

        // 循环踢出用户
        Session kickoutSession;
        while(userList.size() > maxSession){
            Serializable kickoutSessionId = userList.removeLast();
            kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
            if(kickoutSession != null) {
                //设置会话的kickout属性表示踢出了
                kickoutSession.setAttribute("kickout", true);
            }
        }

        return true;
    }
}
