package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.user.User;
import com.cos.security1.model.user.UserRepository;

import lombok.extern.slf4j.Slf4j;


// 시큐리티 설정에서 loginProcessionUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 LoadUserByUsername 함수가 실행

@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    // @함수 종료 시 @AuthenticationPrincipal 생성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[{}] RCV Username >> {}",getClass().getName(), username);
        User userEntity = userRepository.findByUsername(username).orElseGet(()->{
            log.warn("[{}] No User ",getClass().getName());
            return null;
        });

        log.info("[{}] Find User >> {}", getClass().getName(), userEntity.toString());
        return new PrincipalDetails(userEntity);
    }
}
