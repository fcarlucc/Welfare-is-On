package com.servicesservice.app.model;

import com.servicesservice.app.model.enumerator.PillarName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a pillar or category under which services are classified.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pillar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PillarName name;

    /**
     * Constructs a Pillar object with the specified name.
     *
     * @param name The name of the pillar
     */
    public Pillar(PillarName name) {
        this.name = name;
    }
}
