package com.example.sni.service;

public interface EmailService {
    void sendCode(String toEmail, String code);
}
