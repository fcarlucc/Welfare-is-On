package com.apigateway.app.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.apigateway.app.model.Role;

import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of {@link UserDetails} to represent the authenticated user in the application.
 */
public class MyUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final String fullName;
    private final Long id;
    private final List<GrantedAuthority> authorities;
    private final boolean isAccountExpired;
    private final boolean isAccountLocked;
    private final boolean isEnabled;

    /**
     * Constructs an instance of {@link MyUserDetails}.
     *
     * @param username         the username of the user
     * @param password         the password of the user
     * @param authorities      the roles of the user
     * @param isAccountExpired indicates if the account is expired
     * @param isAccountLocked  indicates if the account is locked
     * @param isEnabled        indicates if the account is enabled
     * @param id               the ID of the user
     * @param fullName         the full name of the user
     */
    public MyUserDetails(String username, String password, List<Role> authorities, boolean isAccountExpired, boolean isAccountLocked, boolean isEnabled, Long id, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.authorities = authorities.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());
        this.isAccountExpired = isAccountExpired;
        this.isAccountLocked = isAccountLocked;
        this.isEnabled = isEnabled;
        this.id = id;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return the authorities, never null
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the ID of the user.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the full name of the user.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true if the user's account is valid (i.e., non-expired), false if no longer valid (i.e., expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return true if the user is not locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return true if the user's credentials are valid (i.e., non-expired), false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
