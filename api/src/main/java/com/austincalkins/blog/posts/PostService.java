package com.austincalkins.blog.posts;

import com.austincalkins.blog.posts.exceptions.DuplicatePostException;
import com.austincalkins.blog.posts.exceptions.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    private static final int PAGE_SIZE = 10;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> fetchPosts(int pageNumber) {
        Pageable sortedByCreatedDate =
                PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("createdDate").descending());
        Page<Post> posts = this.postRepository.findAll(sortedByCreatedDate);

        return posts.get().toList();
    }

    public Post createPost(Post post) {
        Optional<Post> existingPost = this.postRepository.findPostByTitle(post.getTitle());

        if(existingPost.isPresent()) {
            throw new DuplicatePostException(String.format("post with title: %s already exists", post.getTitle()));
        }

        LocalDate nowDate = LocalDate.now();
        post.setCreatedDate(nowDate);
        post.setModifiedDate(nowDate);

        return this.postRepository.save(post);
    }

    public Post updatePost(Post post) {
        Optional<Post> existingPost = this.postRepository.findById(post.getId());
        if(existingPost.isEmpty()) {
            throw new PostNotFoundException(String.format("tried to update post with id: %d but post could not be found", post.getId()));
        }

        post.setModifiedDate(LocalDate.now());

        return this.postRepository.save(post);
    }

    public void deletePost(Long postId) {
        Optional<Post> existingPost = this.postRepository.findById(postId);
        if(existingPost.isEmpty()) {
            throw new PostNotFoundException(String.format("tried to delete post with id: %d but post could not be found", postId));
        }
        this.postRepository.deleteById(postId);
    }
}
