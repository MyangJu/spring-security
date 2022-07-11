package com.cos.security1.config.oauth;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.config.auth.provider.FacebookUserInfo;
import com.cos.security1.config.auth.provider.GoogleUserInfo;
import com.cos.security1.config.auth.provider.OAuth2UserInfo;
import com.cos.security1.model.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.cos.security1.model.user.User;
import org.springframework.util.Assert;

import java.util.Map;


@Service
@Slf4j
public class PrincipalOauth2UserSerivce extends DefaultOAuth2UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    // @함수 종료 시 @AuthenticationPrincipal 생성
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("[{}] LoadUser >> {}", getClass().getName(), userRequest.getClientRegistration());
        log.info("[{}] LoadUser >> {}", getClass().getName(), userRequest.getAccessToken().getTokenValue());
        log.info("[{}] LoadUser >> {}", getClass().getName(), userRequest.getClientRegistration().getProviderDetails());
//        log.info("[{}] LoadUser >> {}", getClass().getName(), super.loadUser(userRequest).getAttributes());

        Map<String, Object> oAuth2userAttribute = super.loadUser(userRequest).getAttributes();

        String provider                         = userRequest.getClientRegistration().getRegistrationId(); // "google"
        OAuth2UserInfo oAuth2UserInfo           = null;

        log.info("[{}] OAuth2 Provider >> {}", getClass().getName(), provider);

        if(provider.equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2userAttribute);
        }else if(provider.equals("facebook")){
            oAuth2UserInfo = new FacebookUserInfo(oAuth2userAttribute);
        }else {
            log.error("[{}] No Supported Provider >> {}", getClass().getName(), provider);
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String userName   = provider +"_"+ providerId; // ex) google_1205710297512
        String password   = bCryptPasswordEncoder.encode("OTP_PROJECT"+provider);
        String email      = oAuth2UserInfo.getEmail();
        String userRole   = "ROLE_USER";

        User user = userRepository.findByUsername(userName).orElseGet(()->{
            return //회원가입
                    userRepository.save(User.builder()
                            .username(userName)
                            .password(password)
                            .email(email)
                            .role(userRole)
                            .provider(provider)
                            .providerId(providerId)
                            .build());
        });

        log.info("[{}] LoadUser >> {}", getClass().getName(), user);
//        if(!userRepository.existsByUsername(userName)){ //가입이 안되어있다면
//            User user = userRepository.save(User.builder()
//                            .username(userName)
//                            .password("")
//                            .email(oAuth2userAttribute.get("email").toString())
//                            .role("ROLE_USER")
//                            .provider("google")
//                            .providerId(oAuth2userAttribute.get("sub").toString())
//                            .build());
//        }

        return new PrincipalDetails(user, oAuth2userAttribute);
    }
}
