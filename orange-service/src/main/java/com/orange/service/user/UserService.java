package com.orange.service.user;

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
}
