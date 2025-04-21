    package com.example.sni.controller;

    import com.example.sni.entity.UserEntity;
    import com.example.sni.model.Users;
    import com.example.sni.repository.UserRepository;
    import com.example.sni.requests.CodeVerificationRequest;
    import com.example.sni.requests.RegisterRequest;
    import com.example.sni.service.UserService;
    import jakarta.servlet.http.Cookie;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.apache.catalina.User;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseCookie;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AnonymousAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.*;


    import java.time.Duration;
    import java.time.Instant;
    import java.util.HashMap;
    import java.util.Map;


    @RestController

    public class UserController {

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;


        @PostMapping("/client/auth/register")

        public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
            if (userRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest().body(Map.of("message", "Email already in use"));
            }

            if (userRepository.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest().body(Map.of("message", "Username already in use"));
            }

            UserEntity user = new UserEntity();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("client");
            user.setCreatedAt(Instant.now());
            userRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        }


        @PostMapping("/{role}/auth/login")
        public ResponseEntity<?> login(@PathVariable("role") String role, @RequestBody UserEntity user) {

            UserEntity userEntity = userService.findByUsername(user.getUsername());
            System.out.println("role "+userEntity.getRole());
            if (role.equals("admin") && userEntity.getRole().contains("client")) {

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "You do not have access to the admin app."));
            }
            return userService.start2FALogin(user);
        }


        @GetMapping("/auth/me")
        public ResponseEntity<?> getCurrentUser(Authentication authentication) {
            if (authentication != null && authentication.isAuthenticated()) {

                String username = authentication.getName();
                UserEntity userEntity = userService.findByUsername(username);

                Users user = new Users(userEntity.getId(), userEntity.getUsername(),
                        userEntity.getEmail(), userEntity.getRole());
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
            }
        }
        @PostMapping("/{role}/auth/verify-code")
        public ResponseEntity<String> verifyCode(@RequestBody CodeVerificationRequest request, HttpServletResponse response) {
        String token = userService.verify(request);
        System.out.println("setting token");

        if ("Invalid or expired code".equals(token) || "User not found".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(token);
         }

        ResponseCookie tokenCookie = ResponseCookie.from("token", token)
            .domain("localhost")
            .path("/")
            .maxAge(Duration.ofHours(2))
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());


             return ResponseEntity.ok(token);
        }



        @GetMapping("/auth/check")
        public ResponseEntity<?> checkAuth(HttpServletRequest request) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()
                    && !(authentication instanceof AnonymousAuthenticationToken)) {
                return ResponseEntity.ok().build();
            } else {
                System.out.println("Unauthenticated or anonymous request");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        @PostMapping("/logout")
        public ResponseEntity<?> logout(HttpServletResponse response) {
            Cookie cookie = new Cookie("token", null);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return ResponseEntity.ok("Logged out");
        }



    }
