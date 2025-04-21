package com.example.sni.service;

import com.example.sni.entity.UserEntity;
import com.example.sni.repository.UserRepository;
import com.example.sni.requests.CodeVerificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(12);

    public UserEntity registerUser(UserEntity user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

 /*   @Override
    public String verify(UserEntity user) {

        Authentication auth=authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (auth.isAuthenticated()) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", auth.getAuthorities());  // Dodajemo uloge
            return jwtService.generateToken(claims, user.getUsername());
        }
        return "Failed";


    }

  */
 @Override
 public String verify(CodeVerificationRequest request) {
     // Step 1: Check if the code matches
     if (verificationCodeService.verifyCode(request.getUsername(), request.getCode())) {

         // Step 2: Load user to access role/authorities
         UserEntity user = userRepository.findByUsername(request.getUsername());
         if (user == null) {
             return "User not found";
         }

         // Step 3: Create claims and generate token
         Map<String, Object> claims = new HashMap<>();
         claims.put("role", user.getRole()); // or a list, depending on your setup

         return jwtService.generateToken(claims, user.getUsername());
     }

     return "Invalid or expired code";
 }


    @Override
    public ResponseEntity<String> start2FALogin(UserEntity user) {
     System.out.println("start2FALogin");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (auth.isAuthenticated()) {
                String code = String.format("%06d", new Random().nextInt(999999));
                verificationCodeService.storeCode(user.getUsername(), code);

                UserEntity realUser = userRepository.findByUsername(user.getUsername());
                emailService.sendCode(realUser.getEmail(), code);

                return ResponseEntity.ok("Verification code sent to email.");
            }

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
