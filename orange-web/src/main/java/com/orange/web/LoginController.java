package com.orange.web;

import com.orange.service.user.UserService;
import com.orange.core.web.exception.BusinessException;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by chzq on 2017/3/30.
 */

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping("/login")
    public String login(@RequestParam(value = "userName") String userName,
                      @RequestParam(value ="pwd") String pwd){
        UsernamePasswordToken token = new UsernamePasswordToken(userName, pwd);
        //获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        if(subject.isAuthenticated()){
            System.out.println("用户[" + userName + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
        }else{
            token.clear();
        }
        return "index";
        //throw new BusinessException("测试异常");
    }

}
