package com.apigateway.app.authHandler;

import com.apigateway.app.dto.TokenDto;
import com.apigateway.app.exception.EmailNotFoundException;
import com.apigateway.app.mapper.UserMapper;
import com.apigateway.app.model.Role;
import com.apigateway.app.model.User;
import com.apigateway.app.model.enumerator.RoleName;
import com.apigateway.app.security.model.MyUserDetails;
import com.apigateway.app.service.AuthService;
import com.apigateway.app.service.JwtService;
import com.apigateway.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final AuthService authService;

    /**
     * Handles successful authentication events.
     *
     * @param request        The HTTP servlet request.
     * @param response       The HTTP servlet response.
     * @param authentication The authenticated user's authentication object.
     * @throws IOException If an input or output exception occurs.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String userEmail = authentication.getName();

        User user = userService.findByEmail(userEmail);

        authService.generateOtpAndSendEmail(user.getId(), user.getEmail(), "/verify-email");
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(Collections.singletonMap("email", user.getEmail())));
    }
}
