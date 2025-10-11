package com.commentservice.app.repository;

import com.commentservice.app.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Comment entities in the database.
 */
public interface ICommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieves a list of comments for a specific service ID, ordered by commentedAt.
     * @param serviceId Long representing the service ID for which comments are retrieved.
     * @return List of Comment objects associated with the specified service ID, ordered by commentedAt.
     */
    List<Comment> findAllByServiceIdOrderByCommentedAt(Long serviceId);
}
