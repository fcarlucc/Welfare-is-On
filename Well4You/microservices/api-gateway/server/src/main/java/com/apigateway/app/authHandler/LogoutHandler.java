package com.apigateway.app.authHandler;

import com.apigateway.app.service.JwtService;
import com.apigateway.app.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    /**
     * Handles logout requests by clearing the security context and removing the refresh token cookie.
     *
     * @param request        The HTTP servlet request.
     * @param response       The HTTP servlet response.
     * @param authentication The current authentication object (may be null if not authenticated).
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // Create a new cookie for the refresh token with null value and set properties to invalidate it
        Cookie refreshTokenCookie = new Cookie("refresh_token", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // Set the cookie's max age to 0 seconds to expire it immediately
        response.addCookie(refreshTokenCookie);

        // Clear the security context to ensure no user authentication remains
        SecurityContextHolder.clearContext();
    }
}
