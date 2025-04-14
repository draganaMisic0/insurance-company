package com.example.sni;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("")
    public String hello(HttpServletRequest request) {
        return "Welcome "+request.getSession().getId();
    }
}
