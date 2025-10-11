package com.shopservice.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a purchase made by a user for a service.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase", schema = "shopservice")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    /**
     * Constructs a Purchase object with specified userId and serviceId.
     *
     * @param userId    The ID of the user making the purchase.
     * @param serviceId The ID of the service being purchased.
     */
    public Purchase(Long userId, Long serviceId) {
        this.userId = userId;
        this.serviceId = serviceId;
    }

    /**
     * Returns a string representation of the Purchase object.
     *
     * @return A string representation containing Purchase details.
     */
    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                '}';
    }
}
