package com.cos.security1.config.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.user.User;
import java.util.ArrayList;
// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인이 성공하면 session이 만들어진다. (Security ContextHolder)라는 필드를 가지는데 여기에 Session정보를 저장함.
// 그 안에는 오브젝트 => Authentication 객체가 존재함 ( 이 객체만 들어가야하고 들어가있음 )
// Authentication 안에 User정보가 있어야 됨.
// User오브젝트타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails

public class PrincipalDetails implements UserDetails{

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    /*
     * 해당 User의 권한을 리턴하는 곳
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
           @Override
           public String getAuthority() {
               return user.getRole();
           } 
        });
        
        return collection;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    
}
