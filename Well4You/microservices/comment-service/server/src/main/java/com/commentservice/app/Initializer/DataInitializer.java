package com.commentservice.app.Initializer;

import com.commentservice.app.model.Comment;
import com.commentservice.app.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;


@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final CommentService commentService;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            Clock fixedClock = Clock.fixed(Instant.parse("2024-07-10T10:00:00Z"), ZoneId.of("UTC"));
            OffsetDateTime mockDateTime = OffsetDateTime.now(fixedClock);

            commentService.create(new Comment(1L, 1L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 1L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 1L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 1L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 2L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 2L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 2L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 2L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 3L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 3L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 3L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 3L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 4L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 4L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 4L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 4L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 5L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 5L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 5L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 5L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 6L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 6L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 6L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 6L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 7L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 7L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 7L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 7L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 8L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 8L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 8L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 8L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 9L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 9L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 9L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 9L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 10L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 10L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 10L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 10L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 11L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 11L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 11L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 11L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 12L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 12L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 12L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 12L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 13L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 13L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 13L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 13L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 14L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 14L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 14L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 14L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
            commentService.create(new Comment(1L, 15L, mockDateTime, "Very useful service, I advice it", "Manuele Longo"));
            commentService.create(new Comment(2L, 15L, mockDateTime, "Nice service for all the employees", "Flaviano Carlucci"));
            commentService.create(new Comment(3L, 15L, mockDateTime, "Not bad i had a great experience thanks this service", "Lorenzo Nicotera"));
            commentService.create(new Comment(4L, 15L, mockDateTime, "the best service for everyone", "Alessio Buonomo"));
        };
    }
}
