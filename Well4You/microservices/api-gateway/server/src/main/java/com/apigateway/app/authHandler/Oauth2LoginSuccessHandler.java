package com.apigateway.app.authHandler;

import com.apigateway.app.mapper.UserMapper;
import com.apigateway.app.model.Role;
import com.apigateway.app.model.User;
import com.apigateway.app.model.enumerator.RoleName;
import com.apigateway.app.repository.IUserRepository;
import com.apigateway.app.security.model.MyUserDetails;
import com.apigateway.app.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    // Injecting properties from application.properties or application.yml
    @Value("${client.prot}")
    private String clientProtocol;

    @Value("${client.ip}")
    private String clientIp;

    @Value("${client.port}")
    private int clientPort;

    // Required dependencies
    private final IUserRepository userRepository;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final UserService userService;

    /**
     * Handles successful authentication using OAuth2 provider.
     *
     * @param request        The HTTP servlet request.
     * @param response       The HTTP servlet response.
     * @param authentication The authentication object containing details about the authenticated user.
     * @throws IOException   If an I/O error occurs during the redirect.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email;
        String redirectUrl;
        User newUser = new User();
        String url = clientProtocol + "://" + clientIp + ":" + clientPort;

        // Extract email from OAuth2 user attributes
        email = user.getAttribute("email");

        // Check if user already exists in the database
        Optional<User> userDb = userRepository.findByEmail(email);

        if (userDb.isEmpty()) { // User does not exist in the database
            redirectUrl = url + "/sign-up/survey/?token=";

            // Create a new user entity based on OAuth2 attributes
            newUser.setFirstName(user.getAttribute("given_name"));
            newUser.setLastName(user.getAttribute("family_name"));
            newUser.setEmail(email);

            // Assign default role to the new user
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findByName(RoleName.ROLE_USER).get());
            newUser.setRoles(roles);
            newUser.setExpired(false);
            newUser.setEnabled(true);
            newUser.setBlocked(false);

            try {
                // Create user tables (if necessary)
                authService.createUserTables(newUser);
            } catch (Exception e) {
                // Redirect to sign-in page with error message on server error
                response.sendRedirect(url + "/sign-in/?error=Server error try later");
            }
        } else { // User already exists in the database
            try {
                // Determine the redirect URL based on whether the user profile is completed
                if (!userService.isUserProfiled(email)) {
                    redirectUrl = url + "/sign-up/survey/?token=";
                } else {
                    redirectUrl = url + "/?token=";
                }
            } catch (Exception e) {
                // Default to the sign-up survey page on exception
                redirectUrl = url + "/sign-up/survey/?token=";
            }
            // Retrieve existing user details from the database
            newUser = userDb.get();
        }

        // Convert user details to UserDetails object for JWT token generation
        MyUserDetails userDetails = userMapper.userToUserDetails(newUser);

        // Generate JWT tokens
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // Set refresh token as an HTTP only cookie
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setAttribute("SameSite", "Strict");
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);

        // Redirect the user to the appropriate URL with access token appended
        response.sendRedirect(redirectUrl + accessToken);
    }
}
