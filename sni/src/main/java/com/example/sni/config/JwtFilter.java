package com.example.sni.config;

import com.example.sni.service.JWTService;
import com.example.sni.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {
//  Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxsIiwiaWF0IjoxNzIzMTgzNzExLCJleHAiOjE3MjMxODM4MTl9.5nf7dRzKRiuGurN2B9dHh_M5xiu73ZzWPr6rbhOTTHs
        System.out.println("FILTER CALLED");
       //String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String role=null;
       // System.out.println(authHeader);
       // String token = null;


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
/*
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("first if");
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
            role= jwtService.extractRole(token);
            System.out.println(username);
            System.out.println(role);
            System.out.println("URI: " + request.getRequestURI());
        }
        */
        if (token != null) {
            username = jwtService.extractUserName(token);
            role = jwtService.extractRole(token);
            System.out.println("Token from cookie: " + token);
            System.out.println("Username: " + username);
            System.out.println("Role: " + role);
            System.out.println("URI: " + request.getRequestURI());
        }

        if (username != null && role!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                System.out.println("URI: " + request.getRequestURI());
                String origin = request.getHeader("Origin");
                System.out.println("Origin: " + origin);
                System.out.println("Role "+role);

                if (origin != null) {
                    if (origin.contains("5173") && "client".equals(role)) {
                        System.out.println("first if");
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Clients cannot access Admin App");
                        return;
                    }

                }
                System.out.println("gets to this point");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                System.out.println("auth token: " + authToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
