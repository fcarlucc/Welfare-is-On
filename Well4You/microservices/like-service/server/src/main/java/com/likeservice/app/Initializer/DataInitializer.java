package com.likeservice.app.Initializer;

import com.likeservice.app.model.Like;
import com.likeservice.app.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final LikeService likeService;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            likeService.create(new Like(1L, 1L));
            likeService.create(new Like(2L, 1L));
            likeService.create(new Like(3L, 1L));
            likeService.create(new Like(4L, 1L));
            likeService.create(new Like(5L, 1L));
            likeService.create(new Like(6L, 1L));
            likeService.create(new Like(7L, 1L));
            likeService.create(new Like(1L, 2L));
            likeService.create(new Like(2L, 2L));
            likeService.create(new Like(3L, 2L));
            likeService.create(new Like(1L, 3L));
            likeService.create(new Like(2L, 3L));
            likeService.create(new Like(3L, 3L));
            likeService.create(new Like(4L, 3L));
            likeService.create(new Like(5L, 3L));
            likeService.create(new Like(6L, 3L));
            likeService.create(new Like(1L, 4L));
            likeService.create(new Like(2L, 4L));
            likeService.create(new Like(3L, 4L));
            likeService.create(new Like(4L, 4L));
            likeService.create(new Like(1L, 5L));
            likeService.create(new Like(2L, 5L));
            likeService.create(new Like(1L, 6L));
            likeService.create(new Like(1L, 7L));
            likeService.create(new Like(2L, 7L));
            likeService.create(new Like(3L, 8L));
            likeService.create(new Like(4L, 8L));
            likeService.create(new Like(5L, 8L));
            likeService.create(new Like(6L, 8L));
            likeService.create(new Like(7L, 9L));
            likeService.create(new Like(7L, 10L));
            likeService.create(new Like(7L, 11L));
            likeService.create(new Like(1L, 12L));
            likeService.create(new Like(2L, 12L));
            likeService.create(new Like(3L, 12L));
            likeService.create(new Like(4L, 13L));
            likeService.create(new Like(5L, 13L));
            likeService.create(new Like(3L, 14L));
        };
    }
}
