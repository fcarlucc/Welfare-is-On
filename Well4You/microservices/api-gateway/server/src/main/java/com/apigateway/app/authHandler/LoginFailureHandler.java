package com.apigateway.app.authHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * Handles authentication failure scenarios by setting the HTTP response status to UNAUTHORIZED (401)
     * and returning a JSON response with an error message.
     *
     * @param request    The HTTP servlet request.
     * @param response   The HTTP servlet response.
     * @param exception  The AuthenticationException raised.
     * @throws IOException      If an input or output exception occurs.
     * @throws ServletException If a servlet exception occurs.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper(); // ObjectMapper to convert Java objects to JSON and vice versa
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(Collections.singletonMap("error", exception.getMessage())));
    }

}
