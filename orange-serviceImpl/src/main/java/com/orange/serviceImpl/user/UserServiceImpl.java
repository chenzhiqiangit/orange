package com.orange.serviceImpl.user;

import com.orange.dao.user.UserDao;
import com.orange.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chzq on 2017/3/30.
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    public boolean login(String userName, String pwd) {
        logger.debug("登录验证.......................................");
        return false;
    }
}
