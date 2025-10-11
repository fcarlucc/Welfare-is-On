package com.apigateway.app.authHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Oauth2LoginFailureHandler implements AuthenticationFailureHandler {

    // Injecting properties from application.properties or application.yml
    @Value("${client.prot}")
    private String clientProtocol;

    @Value("${client.ip}")
    private String clientIp;

    @Value("${client.port}")
    private int clientPort;

    /**
     * Redirects the user to a specific URL in case of OAuth2 authentication failure.
     *
     * @param request        The HTTP servlet request.
     * @param response       The HTTP servlet response.
     * @param exception      The authentication exception that occurred.
     * @throws IOException   If an I/O error occurs during the redirect.
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String redirectUrl = clientProtocol + "://" + clientIp + ":" + clientPort + "/sign-in";
        response.sendRedirect(redirectUrl);
    }

}
