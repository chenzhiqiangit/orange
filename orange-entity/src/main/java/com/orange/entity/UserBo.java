package com.orange.entity;

import com.orange.base.BaseBo;

/**
 * Created by chzq on 2017/3/30.
 */
public class UserBo extends BaseBo {
    private Long userId;
    private String userName;
    private String passWord;


    private static final long SerialVersionUID = 1L;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
