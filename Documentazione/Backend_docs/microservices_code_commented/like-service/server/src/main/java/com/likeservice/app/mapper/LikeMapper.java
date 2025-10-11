package com.likeservice.app.mapper;

import com.likeservice.app.dto.LikeDto;
import com.likeservice.app.model.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting LikeDto objects to Like entities.
 */
@Component
@RequiredArgsConstructor
public class LikeMapper {

    /**
     * Converts a LikeDto object to a Like entity.
     *
     * @param likeDto The LikeDto object to convert.
     * @return A new Like entity populated with data from the LikeDto.
     */
    public Like likeDtoToLike(LikeDto likeDto) {
        Like like = new Like();

        like.setServiceId(likeDto.getServiceId());
        like.setUserId(likeDto.getUserId());

        return like;
    }

}
