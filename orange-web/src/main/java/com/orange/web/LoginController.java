package com.orange.web;

import com.orange.service.user.UserService;
import com.orange.web.exception.BusinessException;
import org.apache.log4j.Logger;
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
        boolean loginFlag = userService.login(userName,pwd);
        logger.debug("登录111。。。。。。。。。。。。。。。。");
        throw new BusinessException("测试异常");
    }

    @RequestMapping("/userLogin")
    public String userLogin(){
        logger.debug("登录页面111。。。。。。。。。。。。。。。。");
        return "login";
    }
}
