package com.sercurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping(value = {"/","/home"})
    public String home(){
        return "home";
    }
    @GetMapping("/toLogin")
    public String login(){
        return "login";
    }
    @GetMapping("/user")
    public String user(){
        return "/user/user";
    }
    @GetMapping("/admin")
    public String admin(){
        return "/admin/admin";
    }

}
