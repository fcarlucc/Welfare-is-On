package com.apigateway.app.model;

import lombok.*;

import com.apigateway.app.model.enumerator.RoleName;

import jakarta.persistence.*;

/**
 * Entity class representing a role.
 */
@Data
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName name;

    /**
     * Constructor to initialize a Role object with a specified name.
     * @param name RoleName enum representing the name of the role.
     */
    public Role(RoleName name) {
        this.name = name;
    }
}
