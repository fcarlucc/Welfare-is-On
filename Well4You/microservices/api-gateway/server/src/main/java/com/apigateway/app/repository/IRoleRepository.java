package com.apigateway.app.repository;

import com.apigateway.app.model.Role;
import com.apigateway.app.model.enumerator.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Role entities.
 */
public interface IRoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a Role by its name.
     * @param name RoleName enum representing the name of the role to find.
     * @return Optional Role containing the found Role, or empty if not found.
     */
    Optional<Role> findByName(RoleName name);
}
