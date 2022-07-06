package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.user.User;
import com.cos.security1.model.user.UserRepository;
import com.cos.security1.model.user.UserResponseEntity;
import com.cos.security1.service.IndexService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class IndexController {

    @Autowired
    private IndexService indexService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
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
        return "joinForm";
    }

    @PostMapping(value="/join")
    public String joinForm(User user) {
        log.info("[{}] receive User >> {}", getClass().getName(), user);

        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        
        user.setPassword(encPassword);

        userRepository.save(user);

        return "redirect:/loginForm";
    }

    // @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value="/data")
    public @ResponseBody String info() {
        return "data";
    }
    

    @GetMapping(value="/joinProc")
    public @ResponseBody String joinProc() {
        return new String("회원가입완료");
    }
    
}
