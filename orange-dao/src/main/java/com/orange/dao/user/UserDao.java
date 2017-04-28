package com.orange.dao.user;

import com.orange.common.base.BaseDao;
import com.orange.entity.Permission;
import com.orange.entity.UserBo;

import java.util.List;

/**
 * Created by chzq on 2017/4/7.
 */
public interface UserDao extends BaseDao<UserBo> {
    List<UserBo> findUserAll();
    UserBo findUserById(String userId);

    UserBo findUserByName(String name);

    UserBo selectUserByPwd(String userName,String pwd);

    List<Permission> selectPermissionsByUser(String userId,String accountId);
}
