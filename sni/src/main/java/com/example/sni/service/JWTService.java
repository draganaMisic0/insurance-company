package com.example.sni.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    String generateToken(String username);
    String generateToken(Map<String, Object> claims, String username);
    String extractUserName(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
