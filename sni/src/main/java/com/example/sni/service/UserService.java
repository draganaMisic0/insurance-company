package com.example.sni.service;

import com.example.sni.entity.UserEntity;

public interface UserService {

    public UserEntity registerUser(UserEntity user);

    String verify(UserEntity user);
}
