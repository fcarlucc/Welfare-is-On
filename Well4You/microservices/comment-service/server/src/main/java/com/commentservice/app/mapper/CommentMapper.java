package com.commentservice.app.mapper;

import com.commentservice.app.dto.CommentDto;
import com.commentservice.app.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper component for mapping between CommentDto and Comment entities.
 */
@Component
@RequiredArgsConstructor
public class CommentMapper {

    /**
     * Converts CommentDto to Comment entity.
     * @param commentDto The CommentDto object to be converted.
     * @return Comment entity mapped from CommentDto.
     */
    public Comment commentDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setServiceId(commentDto.getServiceId());
        comment.setUserId(commentDto.getUserId());
        comment.setCommentedAt(commentDto.getCommentedAt());
        comment.setContent(commentDto.getContent());
        comment.setFullName(commentDto.getFullName());

        return comment;
    }

}
