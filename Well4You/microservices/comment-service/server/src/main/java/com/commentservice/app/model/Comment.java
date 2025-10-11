package com.commentservice.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Entity class representing a comment made by a user on a service.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment", schema = "commentservice")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(updatable = false, nullable = false, name = "commented_at")
    private OffsetDateTime commentedAt;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    /**
     * Constructor to initialize a Comment object with specified attributes.
     * @param userId Long representing the user ID who made the comment.
     * @param serviceId Long representing the service ID on which the comment was made.
     * @param commentedAt OffsetDateTime representing the date and time when the comment was made.
     * @param content String representing the content of the comment.
     * @param fullName String representing the full name of the user who made the comment.
     */
    public Comment(Long userId, Long serviceId, OffsetDateTime commentedAt, String content, String fullName) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.commentedAt = commentedAt;
        this.content = content;
        this.fullName = fullName;
    }

    /**
     * Returns a string representation of the Comment object.
     * @return String representing the Comment object's state.
     */
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                ", commentedAt=" + commentedAt +
                ", content='" + content + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
