package com.commentservice.app.service;

import com.commentservice.app.model.Comment;
import com.commentservice.app.exception.CommentNotFoundException;
import com.commentservice.app.repository.ICommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Comment entities.
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final ICommentRepository commentRepository;

    /**
     * Retrieves a comment by its ID.
     * @param id Long representing the ID of the comment to retrieve.
     * @return Comment object corresponding to the provided ID.
     * @throws CommentNotFoundException if no comment exists with the given ID.
     */
    public Comment findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new CommentNotFoundException("Comment id does not exist");
        }
        return comment.get();
    }

    /**
     * Retrieves all comments associated with a specific service.
     * @param serviceId Long representing the ID of the service for which comments are retrieved.
     * @return List of Comment objects associated with the specified service ID, ordered by commentedAt.
     */
    public List<Comment> getCommentsByService(Long serviceId) {
        return commentRepository.findAllByServiceIdOrderByCommentedAt(serviceId);
    }

    /**
     * Creates a new comment.
     * @param comment Comment object to be created.
     */
    public void create(Comment comment) {
        commentRepository.save(comment);
    }

    /**
     * Updates an existing comment.
     * @param comment Comment object to be updated.
     */
    public void update(Comment comment) {
        commentRepository.save(comment);
    }

}
