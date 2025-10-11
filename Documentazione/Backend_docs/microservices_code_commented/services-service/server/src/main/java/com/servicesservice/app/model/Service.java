package com.servicesservice.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a service offered within the application.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service", schema = "servicesservice")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long imageId;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer discount;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @ManyToOne
    @JoinColumn(name = "pillar_id")
    private Pillar pillar;

    @Column(nullable = false)
    private String url;

    /**
     * Constructs a Service object with basic attributes.
     *
     * @param description The description of the service
     * @param title       The title of the service
     * @param imageId     The ID of the image associated with the service
     * @param price       The price of the service
     * @param discount    The discount applicable to the service
     * @param pillar      The pillar under which the service falls
     * @param url         The URL associated with the service
     */
    public Service(String description, String title, Long imageId, Double price, Integer discount, Pillar pillar, String url) {
        this.description = description;
        this.title = title;
        this.imageId = imageId;
        this.price = price;
        this.discount = discount;
        this.pillar = pillar;
        this.url = url;
    }

    /**
     * Constructs a Service object with additional geolocation attributes.
     *
     * @param description The description of the service
     * @param title       The title of the service
     * @param imageId     The ID of the image associated with the service
     * @param price       The price of the service
     * @param discount    The discount applicable to the service
     * @param pillar      The pillar under which the service falls
     * @param longitude   The longitude coordinate of the service location
     * @param latitude    The latitude coordinate of the service location
     * @param url         The URL associated with the service
     */
    public Service(String description, String title, Long imageId, Double price, Integer discount, Pillar pillar, Double longitude, Double latitude, String url) {
        this.description = description;
        this.title = title;
        this.imageId = imageId;
        this.price = price;
        this.discount = discount;
        this.pillar = pillar;
        this.longitude = longitude;
        this.latitude = latitude;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", imageId=" + imageId +
                ", price=" + price +
                '}';
    }
}
