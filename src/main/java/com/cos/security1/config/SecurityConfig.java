package com.cos.security1.config;

import com.cos.security1.config.oauth.PrincipalOauth2UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.FailFastProblemReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화 특정메소드에 Secure 룰을 걸 수 있다.
//preAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private PrincipalOauth2UserSerivce principalOauth2UserSerivce;

    @Bean
    public BCryptPasswordEncoder encodePwd(){

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf 인증off
        http.authorizeRequests()
          .antMatchers("/user/**")
          .authenticated()// /user/**이 인증만 통과된다면 통과
          .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
          .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
          .anyRequest() // 위 url 이외의 Request는 
          .permitAll() // 모두 자동승인
          .and() // 하고
          .formLogin() //로그인폼에 대해서는
          .loginPage("/loginForm") // url은 /loginForm이고
          .loginProcessingUrl("/login")// //loginForm에서 로그인로직을 저리해주는 프로세스 url은 /login ( /login 주소가 호출이 되면 시큐리티가 대신 로그인을 진행함 )
          .defaultSuccessUrl("/") //로그인이 성공하면 / url로 이동
                .and()
                .oauth2Login()
                .loginPage("/loginForm")// 구글 로그인 후 후처리 필요. Tip: 코드X, 액세스토큰+사용자 프로필정보 O
                .userInfoEndpoint()
                .userService(principalOauth2UserSerivce);

    }
}
