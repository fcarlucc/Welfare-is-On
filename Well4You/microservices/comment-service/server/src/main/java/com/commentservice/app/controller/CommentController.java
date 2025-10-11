package com.commentservice.app.controller;

import com.commentservice.app.dto.CommentDto;
import com.commentservice.app.mapper.CommentMapper;
import com.commentservice.app.model.Comment;
import com.commentservice.app.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling comments related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    /**
     * Endpoint to create a new comment.
     * @param commentDto The DTO containing comment data.
     * @return ResponseEntity containing the created Comment object.
     */
    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody @Valid CommentDto commentDto) {
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        commentService.create(comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve comments for a specific service.
     * @param serviceId The ID of the service to retrieve comments for.
     * @return ResponseEntity containing a list of Comment objects.
     */
    @GetMapping("/service-comments")
    public ResponseEntity<List<Comment>> getServiceComments(@RequestParam Long serviceId) {
        List<Comment> comments = commentService.getCommentsByService(serviceId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
