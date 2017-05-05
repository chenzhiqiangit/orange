package com.orange.serviceImpl.user;

import com.orange.dao.system.SystemMapper;
import com.orange.dao.user.UserMapper;
import com.orange.entity.Permission;
import com.orange.entity.UserBo;
import com.orange.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by chzq on 2017/3/30.
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SystemMapper systemMapper;

    public boolean login(String userName, String pwd) {
        logger.debug("登录验证.......................................");
        return false;
    }

    public UserBo selectUserByPwd(String userName,String pwd){
        Map<String,String> param = new HashMap();
        param.put("userName",userName);
        param.put("pwd",pwd);
        return userMapper.selectByUserPwd(param);
    }

    public List<UserBo> selectUsers(String accountId) {
        return userMapper.selectAll();
    }

    public UserBo selectUserById(String userId) {
        return userMapper.selectById(userId);
    }

    public UserBo selectUserByName(String userName) {
        return userMapper.selectByName(userName);
    }

    public List <Permission> selectPermissionsByUser(Long userId, String accountId) {
        return systemMapper.selectPermissionByUser(userId);
    }

    public List<String> selectPermissionsByUserToStr(Long userId, String accountId) {
        List <Permission> list = this.selectPermissionsByUser(userId, null);
        List <String> permissionStrList = new ArrayList <String>();
        for (Permission p : list) {
            String actions = p.getAttachedActions();
            if (actions != null && !actions.equals("")) {
                permissionStrList.addAll(Arrays.asList(actions.split(",")));
            }
        }
        return permissionStrList;
    }
}
