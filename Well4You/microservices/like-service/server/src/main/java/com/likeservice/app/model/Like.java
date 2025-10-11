package com.likeservice.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents a 'like' entity in the application.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "like", schema = "likeservice")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    /**
     * Constructs a new Like object with specified userId and serviceId.
     *
     * @param userId    The ID of the user who liked the service.
     * @param serviceId The ID of the service being liked.
     */
    public Like(Long userId, Long serviceId) {
        this.userId = userId;
        this.serviceId = serviceId;
    }

    /**
     * Returns a string representation of the Like object.
     *
     * @return A string containing the ID of the like, user ID, and service ID.
     */
    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                '}';
    }
}
