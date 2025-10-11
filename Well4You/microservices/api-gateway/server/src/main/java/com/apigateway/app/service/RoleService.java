package com.apigateway.app.service;

import com.apigateway.app.model.enumerator.RoleName;
import com.apigateway.app.repository.IRoleRepository;
import com.apigateway.app.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Role entities.
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final IRoleRepository roleRepository;

    /**
     * Finds a Role by its name.
     * @param roleName RoleName enum representing the name of the role to find.
     * @return Optional Role containing the found Role, or empty if not found.
     */
    public Optional<Role> findByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }

    /**
     * Saves a Role entity.
     * @param role Role object to be saved.
     * @return Role object that was saved.
     */
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Retrieves all Role entities.
     * @return List Role containing all Role entities.
     */
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
