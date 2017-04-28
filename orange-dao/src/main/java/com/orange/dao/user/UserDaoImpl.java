package com.orange.dao.user;

import com.orange.common.base.BaseDaoImpl;
import com.orange.entity.Permission;
import com.orange.entity.UserBo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chzq on 2017/4/7.
 */

@Repository
public class UserDaoImpl extends BaseDaoImpl<UserBo> implements UserDao {

    public List<UserBo> findUserAll() {
        return selectAll();
    }

    public UserBo findUserById(String userId) {
        return selectById(userId);
    }

    public UserBo findUserByName(String name) {
        return  sqlSessionTemplate.selectOne(getSqlName("selectByName"), name);
    }

    public UserBo selectUserByPwd(String userName, String pwd) {
        Map<String, Object> params  = new HashMap<String,Object>();
        params.put("userName",userName);
        params.put("pwd",pwd);
        return  sqlSessionTemplate.selectOne(getSqlName("userLogin"), params);
    }

    public List <Permission> selectPermissionsByUser(String userId, String accountId) {
        Map<String, Object> params  = new HashMap<String,Object>();
        params.put("userId",userId);
        params.put("accountId",accountId);
        return sqlSessionTemplate.selectList("selectPermissionsByUser",params);
    }
}
