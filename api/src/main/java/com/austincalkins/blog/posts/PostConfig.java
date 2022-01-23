package com.austincalkins.blog.posts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;

// TODO figure out how to move this out of code for testing and seeding
@Configuration
public class PostConfig {

    @Bean
    CommandLineRunner commandLineRunner(PostRepository repository) {
        return args -> {
            Post firstPost = new Post(
                    1L,
                    "my very first Blog Post",
                    LocalDate.of(2020, OCTOBER, 5)
            );

            Post secondPost = new Post(
                    2L,
                    "my Second Blog Post",
                    LocalDate.of(2020, NOVEMBER, 5)
            );

//            repository.saveAll(List.of(firstPost, secondPost));
        };
    }
}
