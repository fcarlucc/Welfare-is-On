package com.apigateway.app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom authentication entry point that handles unauthorized access attempts.
 * Sets the HTTP response status to 401 Unauthorized and returns an error message.
 */
@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles unauthorized access attempts by setting the response status to 401 and returning an error message.
     *
     * @param request       the HTTP request
     * @param response      the HTTP response
     * @param authException the authentication exception
     * @throws IOException if an input or output error occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("You are not authenticated");
    }
}
