package com.austincalkins.blog.post;

import com.austincalkins.blog.posts.Post;
import com.austincalkins.blog.posts.PostRepository;
import com.austincalkins.blog.posts.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest

public class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();
        List<Post> seedData = this.setupTestSeedData();
        postRepository.saveAll(seedData);
    }

    @Test
    public void fetchPosts_willFetchInNewestFirstOrder() {
        List<Post> posts = this.postService.fetchPosts(0);

        assertNotNull(posts);
        assertEquals(posts.size(), 10);

        Post post = posts.get(0);
        assertEquals(post.getTitle(), "Post - 15");

        post = posts.get(1);
        assertEquals(post.getTitle(), "Post - 14");

        post = posts.get(2);
        assertEquals(post.getTitle(), "Post - 13");
    }

    @Test
    public void fetchPosts_willPageResults() {
        List<Post> posts = this.postService.fetchPosts(0);

        assertNotNull(posts);
        assertEquals(posts.size(), 10);;

        Post post = posts.get(0);
        assertEquals(post.getTitle(), "Post - 15");

        posts = this.postService.fetchPosts(1);

        assertEquals(posts.size(), 5);

        post = posts.get(0);
        assertEquals(post.getTitle(), "Post - 5");
    }

    private List<Post> setupTestSeedData() {
        List<Post> posts = new ArrayList<>();
        /*
            create 15 posts with a title containing the post number
            date is incremented by 1 day for each post so post 15 will fall on january 15th
         */
        for (int postCount = 1; postCount < 16; postCount++) {
                Post post = new Post();
                LocalDate createdDate = LocalDate.of(2021, Month.JANUARY, postCount);
                post.setCreatedDate(createdDate);
                post.setModifiedDate(createdDate);
                post.setTitle("Post - " + postCount);

                posts.add(post);
        }

        return posts;
    }

}
