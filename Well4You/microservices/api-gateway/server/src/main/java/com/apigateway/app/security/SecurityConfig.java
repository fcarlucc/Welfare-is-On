package com.apigateway.app.security;

import com.apigateway.app.authHandler.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

/**
 * Security configuration class for the API Gateway application.
 * This class configures security settings, including authentication and authorization mechanisms.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Qualifier("customAuthenticationEntryPoint")
    private final AuthenticationEntryPoint authEntryPoint;

    @Qualifier("customAccessDeniedHandler")
    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final RequestLimitFilter requestLimitFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LoginSuccessHandler loginSuccessHandler;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final Oauth2LoginFailureHandler oauth2LoginFailureHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    private final String[] coachApiEndpoints = {
            "/api/schedule/set-availability",
            "api/schedule/get-slots-coach"
    };

    private final String[] userApiEndpoints = {
            "/api/schedule/set-booking",
            "/api/schedule/get-availability-days-coach",
            "/api/schedule/get-daily-availability-coach",
    };

    /**
     * Configures the security filter chain.
     *
     * @param http the {@link HttpSecurity} to modify
     * @return the {@link SecurityFilterChain} object
     * @throws Exception if an error occurs while configuring security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .addFilterBefore(requestLimitFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .requiresChannel(channel ->
                        channel.anyRequest().requiresSecure())
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/auth/**",
                                "/api/user/survey-google")
                        .permitAll()
                        .requestMatchers("/secure/api/auth/create-coach").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/services/create").hasAnyAuthority("ROLE_ADMIN", "ROLE_COACH")
                        .requestMatchers(coachApiEndpoints).hasAnyAuthority("ROLE_COACH")
                        .requestMatchers(userApiEndpoints).hasAnyAuthority("ROLE_USER")
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers
                        .xssProtection(
                                xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                        .contentSecurityPolicy(
                                cps -> cps.policyDirectives("script-src 'self'"))
                        .frameOptions(Customizer.withDefaults()))
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/api/auth/oauth2-login")
                        .successHandler(oauth2LoginSuccessHandler)
                        .failureHandler(oauth2LoginFailureHandler))
                .formLogin(formLogin -> formLogin
                        .loginPage("/api/auth/sign-in")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .build();
    }
}
