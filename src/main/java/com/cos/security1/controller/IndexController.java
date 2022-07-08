package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @GetMapping(value = "/test/login")
    public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
        log.info("[{}] /test/login ----------authentication----------",getClass().getName());
        log.info("[{}] authentication >> {}",getClass().getName(), authentication.getPrincipal());
        log.info("[{}] /test/login ----------authentication----------",getClass().getName());
        log.info("AND ");
        log.info("[{}] /test/login ----------UserDetails----------",getClass().getName());
        PrincipalDetails principalDetails =  (PrincipalDetails)authentication.getPrincipal();
        log.info("[{}] authentication >> {}",getClass().getName(), userDetails.getUser());
        log.info("[{}] /test/login ----------UserDetails----------",getClass().getName());


        return "세션정보 확인하기";
    }

    @GetMapping(value = "/test/oauth/login")
    public @ResponseBody String oauthLoginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails oAuth2){
        log.info("[{}] /test/oauth/login ----------authentication----------",getClass().getName());
        log.info("[{}] authentication >> {}",getClass().getName(), authentication.getPrincipal());
        log.info("[{}] /test/login ----------authentication----------",getClass().getName());
        log.info("AND ");
        log.info("[{}] /test/login ----------UserDetails----------",getClass().getName());
//        OAuth2User oAuth2User =  (OAuth2User)authentication.getPrincipal();
//        log.info("[{}] authentication >> {}",getClass().getName(), oAuth2User.getAttributes());
//        log.info("[{}] /test/login ----------UserDetails----------",getClass().getName());
        log.info("[{}] authentication >> {}",getClass().getName(), oAuth2.getAttributes());
        log.info("[{}] /test/login ----------UserDetails----------",getClass().getName());


        return "OAuth 세션정보 확인하기";
    }

    //머스테치 기본폴더 src/main/resources/

    @GetMapping(value="/")
    public String getMethodName() {
        return new String("index");
    }
    
    @GetMapping(value="/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        log.info("[{}] OAuth2 User >> {}", getClass().getName(), principalDetails.getUser());
        return principalDetails.getUser().toString();
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
