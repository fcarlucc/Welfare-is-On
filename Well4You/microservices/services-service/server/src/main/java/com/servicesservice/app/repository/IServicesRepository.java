package com.servicesservice.app.repository;

import com.servicesservice.app.model.Pillar;
import com.servicesservice.app.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing Service entities in the database.
 */
public interface IServicesRepository extends JpaRepository<Service, Long> {

    /**
     * Retrieves all services belonging to a specific pillar.
     *
     * @param pillar The pillar entity for which services are retrieved
     * @return A list of services belonging to the specified pillar
     */
    List<Service> findAllByPillar(Pillar pillar);
}
