package com.orange.serviceImpl.user;

import com.orange.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by chzq on 2017/3/30.
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    public boolean login(String userName, String pwd) {
        logger.debug("登录验证.......................................");
        return false;
    }
}
