package com.example.sni.service;

public interface VerificationCodeService {

    void storeCode(String username, String code);
    boolean verifyCode(String username, String code);


}
