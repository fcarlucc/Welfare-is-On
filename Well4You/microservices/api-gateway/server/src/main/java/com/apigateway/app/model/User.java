package com.apigateway.app.model;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Email;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity class representing a user.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "apigateway")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "is_expired")
    private boolean isExpired;

    /**
     * Constructor to initialize a User object with specified attributes.
     * @param firstName String representing the first name of the user.
     * @param lastName String representing the last name of the user.
     * @param email String representing the email address of the user.
     * @param password String representing the password of the user.
     * @param roles Set Role containing roles assigned to the user.
     * @param isBlocked boolean indicating whether the user is blocked.
     * @param isEnabled boolean indicating whether the user is enabled.
     * @param isExpired boolean indicating whether the user is expired.
     */
    public User(String firstName, String lastName, String email, String password, Set<Role> roles, boolean isBlocked, boolean isEnabled, boolean isExpired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isBlocked = isBlocked;
        this.isEnabled = isEnabled;
        this.isExpired = isExpired;
    }

    /**
     * Returns a list of roles assigned to the user.
     * @return List Role containing roles assigned to the user.
     */
    public List<Role> getAuthorities() {
        return roles.stream().toList();
    }

    /**
     * Returns a string representation of the User object.
     * @return String representing the User object's state.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", isBlocked=" + isBlocked +
                ", isEnabled=" + isEnabled +
                ", isExpired=" + isExpired +
                '}';
    }
}
