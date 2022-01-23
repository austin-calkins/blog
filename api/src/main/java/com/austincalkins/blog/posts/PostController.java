package com.austincalkins.blog.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping(path = "{pageNumber}")
    public List<Post> fetchPosts(@PathVariable("pageNumber") int pageNumber) {
       return this.postService.fetchPosts(pageNumber);
    }

    @PostMapping
    public void createPost(@RequestBody Post post) {
        this.postService.createPost(post);
    }

    @PutMapping
    public void updatePost(@RequestBody Post post) {
        this.postService.updatePost(post);
    }

    @DeleteMapping(path = "{postId}")
    public void deletePost(@PathVariable("postId") long postId) {
        this.postService.deletePost(postId);
    }

}
