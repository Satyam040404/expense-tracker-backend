package com.satyam.expensetracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.satyam.expensetracker.security.CurrentUserHolder;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            String email = jwtService.extractEmail(token);

            CurrentUserHolder.setEmail(email);

            System.out.println("User Email: " + email);

            System.out.println(
                    "Stored Email: "
                            + CurrentUserHolder.getEmail()
            );
        }

        filterChain.doFilter(request, response);
    }
}