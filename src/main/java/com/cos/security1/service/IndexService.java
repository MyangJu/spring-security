package com.cos.security1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.security1.model.user.User;
import com.cos.security1.model.user.UserRepository;
import com.cos.security1.model.user.UserResponseEntity;

import java.util.Optional;


@Service
public class IndexService {
    

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(long id){

        return userRepository.findById(id);
    }

    public boolean checkUserNameDuplicate(long id){
        return userRepository.existsById(id);
    }

}
