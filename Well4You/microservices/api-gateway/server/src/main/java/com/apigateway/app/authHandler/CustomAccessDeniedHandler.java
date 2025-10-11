package com.apigateway.app.authHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles access denied scenarios by setting the HTTP response status to UNAUTHORIZED (401).
     * Writes a simple message indicating "You are not Authorized".
     *
     * @param request               The HTTP servlet request.
     * @param response              The HTTP servlet response.
     * @param accessDeniedException The AccessDeniedException raised.
     * @throws IOException      If an input or output exception occurs.
     * @throws ServletException If a servlet exception occurs.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("\n\n\n entered access denied handler \n\n\n"); // Log statement (optional)
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("You are not Authorized");
    }
}
