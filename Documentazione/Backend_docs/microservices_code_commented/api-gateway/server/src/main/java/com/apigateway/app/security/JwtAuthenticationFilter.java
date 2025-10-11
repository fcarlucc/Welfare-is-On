package com.apigateway.app.security;

import java.io.IOException;

import com.apigateway.app.security.service.MyUserDetailsService;
import com.apigateway.app.service.AuthService;
import com.apigateway.app.service.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpStatus;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

/**
 * Filter that validates JWT tokens in the incoming requests.
 * Extends {@link OncePerRequestFilter} to ensure it's executed once per request.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;
    private final AuthService authService;

    /**
     * Filters each request to validate the JWT token and authenticate the user.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/api/auth/") || request.getRequestURI().startsWith("/api/user/survey-google")) {
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                jwt = authorizationHeader.substring(7);
                username = jwtService.extractUsername(jwt);
            } catch (io.jsonwebtoken.security.SecurityException e) {
                System.out.println("\n\n Jwt signature wrong \n\n");
                unauthorized(response);
                return;
            } catch (io.jsonwebtoken.ExpiredJwtException e) {
                System.out.println("\n\n token expired but valid \n\n");
                username = e.getClaims().getSubject();
                try {
                    UserDetails user = this.userDetailsService.loadUserByUsername(username);
                    System.out.println("\n\n\nRefreshing the token\n\n" + username + " == " + user.getUsername() + "\n");
                    if (!username.equals(user.getUsername()) || !authService.refreshToken(request, response)) {
                        System.out.println("refresh token failed or username is not valid");
                        unauthorized(response);
                        return;
                    }
                    System.out.println("\n\n\n\ntoken is refreshed continuing the request: " + user.getAuthorities() + "\n\n\n\n");
                    successfulAuthentication(request, response, chain, user);
                    return;
                } catch (UsernameNotFoundException ex) {
                    unauthorized(response);
                    return;
                }
            }
        } else {
            System.out.println("\n\n There isn't the access token \n\n");
            unauthorized(response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails user = this.userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, user)) {
                    System.out.println("\n\n\n\ntoken is valid : " + user.getAuthorities() + "\n\n\n\n");
                    successfulAuthentication(request, response, chain, user);
                }
            } catch (Exception e) {
                unauthorized(response);
            }
        }
    }

    /**
     * Sets the response status to 401 Unauthorized and writes an error message.
     *
     * @param response the HTTP response
     * @throws IOException if an input or output error occurs
     */
    public void unauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("You are not authenticated");
    }

    /**
     * Sets the authentication in the security context and continues the filter chain.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain
     * @param user     the authenticated user
     * @throws IOException      if an input or output error occurs
     * @throws ServletException if an error occurs during filtering
     */
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, UserDetails user) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(request, response);
    }
}
