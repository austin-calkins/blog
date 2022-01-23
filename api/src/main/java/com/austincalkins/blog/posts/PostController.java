package com.austincalkins.blog.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> fetchPosts() {
       return this.postService.fetchPosts(0);
    }

    @PostMapping
    public void createPost(@RequestBody Post post) {
        this.postService.createPost(post);
    }

    @PutMapping
    public void updatePost(@RequestBody Post post) {
        this.postService.updatePost(post);
    }

    @DeleteMapping
    public void deletePost(long postId) {
        this.postService.deletePost(postId);
    }

}
