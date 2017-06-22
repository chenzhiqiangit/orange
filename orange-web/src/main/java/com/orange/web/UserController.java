package com.orange.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.orange.entity.UserBo;
import com.orange.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findUserList")
    public ModelAndView findUserByAccoundId(@RequestParam(value = "accountId") String accountId){
        List<UserBo> list = userService.selectUsers(accountId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userList",list);
        return modelAndView;
    }

    @RequestMapping("/userList")
    public void userList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("attr","你好");
        //throw new RuntimeException("错误了");
    }
}
