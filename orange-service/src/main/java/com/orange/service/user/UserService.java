package com.orange.service.user;

import com.orange.entity.UserBo;

import java.util.List;

/**
 * Created by chzq on 2017/3/30.
 */
public interface UserService {

    /**
     *
     * @param userName 用户名
     * @param pwd  密码
     * @return
     */
    boolean login(String userName,String pwd);

    /**
     * @param accountId 账号ID
     * @return 查询账户的用户
     */
    List<UserBo> selectUsers(String accountId);
}
