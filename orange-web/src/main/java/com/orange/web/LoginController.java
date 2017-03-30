package com.orange.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chzq on 2017/3/30.
 */

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "index";
    }

}
