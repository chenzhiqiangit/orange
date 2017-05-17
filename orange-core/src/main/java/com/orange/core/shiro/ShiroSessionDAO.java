package com.orange.core.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by chzq on 2017/5/17.
 */
public class ShiroSessionDAO extends AbstractSessionDAO{
    protected Serializable doCreate(Session session) {
        return null;
    }

    protected Session doReadSession(Serializable serializable) {
        return null;
    }

    public void update(Session session) throws UnknownSessionException {

    }

    public void delete(Session session) {

    }

    public Collection<Session> getActiveSessions() {
        return null;
    }
}
