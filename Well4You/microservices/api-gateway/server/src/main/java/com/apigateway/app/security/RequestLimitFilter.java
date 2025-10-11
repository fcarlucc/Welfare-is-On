package com.apigateway.app.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Filter that limits the number of requests a client can make per second.
 * Prevents clients from exceeding a predefined rate limit.
 */
@Component
public class RequestLimitFilter implements Filter {

    private static final int MAX_REQUESTS_PER_SECOND = 150;
    private static final int MAX_REQUESTS_PER_SECOND_LOGIN = 5;
    private Map<String, Integer> requestCountMap = new HashMap<>();
    private Map<String, Long> requestTimestampMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed for this filter
    }

    /**
     * Filters incoming requests and enforces rate limits.
     *
     * @param servletRequest  the incoming request
     * @param servletResponse the outgoing response
     * @param filterChain     the filter chain
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if a servlet error is detected
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String clientIP = servletRequest.getRemoteAddr();

        if (isRequestLimitExceeded(clientIP, (HttpServletResponse) servletResponse, (HttpServletRequest) servletRequest)) {
            return;
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        // No resource cleanup needed for this filter
    }

    /**
     * Checks if the request limit has been exceeded for the given client IP.
     *
     * @param clientIP     the IP address of the client
     * @param httpResponse the HTTP response
     * @param httpRequest  the HTTP request
     * @return true if the request limit has been exceeded, false otherwise
     */
    private boolean isRequestLimitExceeded(String clientIP, HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
        try {
            long currentTime = System.currentTimeMillis();

            if (requestCountMap.containsKey(clientIP)) {
                int count = requestCountMap.get(clientIP);
                long lastRequestTime = requestTimestampMap.get(clientIP);
                long timeElapsed = currentTime - lastRequestTime;
                String endpoint = httpRequest.getServletPath();

                if (count >= MAX_REQUESTS_PER_SECOND && timeElapsed < 1000) {
                    httpResponse.setStatus(429);
                    httpResponse.getWriter().write("Request limit per second exceeded.");
                    return true;
                } else if (endpoint.equals("/api/auth/sign-in") && count >= MAX_REQUESTS_PER_SECOND_LOGIN && timeElapsed < 1000) {
                    httpResponse.setStatus(429);
                    httpResponse.getWriter().write("Login request limit per second exceeded.");
                    return true;
                } else if (timeElapsed >= 1000) {
                    requestCountMap.put(clientIP, 1);
                    requestTimestampMap.put(clientIP, currentTime);
                } else {
                    requestCountMap.put(clientIP, count + 1);
                }
            } else {
                requestCountMap.put(clientIP, 1);
                requestTimestampMap.put(clientIP, currentTime);
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
