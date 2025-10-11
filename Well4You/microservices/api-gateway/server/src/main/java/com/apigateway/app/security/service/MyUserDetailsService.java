package com.apigateway.app.security.service;

import com.apigateway.app.mapper.UserMapper;
import com.apigateway.app.model.User;
import com.apigateway.app.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for loading user-specific data.
 * Implements {@link UserDetailsService} to fetch user details from the database.
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Loads the user details by username.
     *
     * @param username the username identifying the user whose data is required
     * @return a fully populated {@link UserDetails} object
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return userMapper.userToUserDetails(user);
    }
}
