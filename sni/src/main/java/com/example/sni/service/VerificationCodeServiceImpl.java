package com.example.sni.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private static final int CODE_EXPIRATION_MINUTES = 5;

    private static class CodeEntry {
        String code;
        LocalDateTime expiresAt;

        CodeEntry(String code, LocalDateTime expiresAt) {
            this.code = code;
            this.expiresAt = expiresAt;
        }
    }

    private final Map<String, CodeEntry> codeStorage = new ConcurrentHashMap<>();

    @Override
    public void storeCode(String username, String code) {
        codeStorage.put(username, new CodeEntry(code, LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES)));
    }

    @Override
    public boolean verifyCode(String username, String code) {
        CodeEntry entry = codeStorage.get(username);
        if (entry == null) return false;

        // Check if code is correct and not expired
        boolean isValid = entry.code.equals(code) && LocalDateTime.now().isBefore(entry.expiresAt);

        if (isValid) {
            codeStorage.remove(username); // one-time use
        }

        return isValid;
    }
}
