package com.orange.web;

import com.orange.entity.UserBo;
import com.orange.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by chzq on 2017/4/7.
 */

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/userList")
    @ResponseBody
    public void findUserByAccoundId(@RequestParam(value = "accountId") String accountId){
        List<UserBo> list = userService.selectUsers(accountId);
    }
}
