package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController {
    
    //머스테치 기본폴더 src/main/resources/

    @GetMapping(value="/")
    public String getMethodName() {
        return new String("index");
    }
    
    @GetMapping(value="/user")
    public @ResponseBody String user() {
        return new String("user");
    }

    @GetMapping(value="/admin")
    public @ResponseBody String admin() {
        return new String();
    }

    @GetMapping(value="/manager")
    public @ResponseBody String manager() {
        return new String();
    }

    @GetMapping(value="/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping(value="/joinForm")
    public String joinForm() {
        return "loginForm";
    }

    @GetMapping(value="/join")
    public @ResponseBody String join() {
        return new String("join");
    }

    @GetMapping(value="/joinProc")
    public @ResponseBody String joinProc() {
        return new String("회원가입완료");
    }
    
}
