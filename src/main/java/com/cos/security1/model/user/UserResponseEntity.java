package com.cos.security1.model.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseEntity {
    private String userName;
    private String Authtoken;
    
}
