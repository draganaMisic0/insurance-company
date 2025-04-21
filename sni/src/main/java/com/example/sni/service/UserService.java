package com.example.sni.service;

import com.example.sni.entity.UserEntity;
import com.example.sni.requests.CodeVerificationRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public UserEntity registerUser(UserEntity user);

    //String verify(UserEntity user);
    public ResponseEntity<String> start2FALogin(UserEntity user);
    public String verify(CodeVerificationRequest request);

    UserEntity findByUsername(String username);
}
