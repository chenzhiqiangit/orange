package com.orange.dao.user;

import com.orange.common.base.BaseDaoImpl;
import com.orange.entity.UserBo;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
