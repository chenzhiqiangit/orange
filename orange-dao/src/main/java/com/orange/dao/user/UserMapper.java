package com.orange.dao.user;

import com.orange.entity.Permission;
import com.orange.entity.UserBo;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * Created by chzq on 2017/5/4.
 */

@MapperScan
public interface UserMapper  {

    List<UserBo> selectAll();
    UserBo selectById(String userId);
    UserBo selectByName(String name);
    UserBo selectByUserPwd(Map param);
}
