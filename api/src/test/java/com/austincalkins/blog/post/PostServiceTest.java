package com.austincalkins.blog.post;

import com.austincalkins.blog.posts.Post;
import com.austincalkins.blog.posts.PostRepository;
import com.austincalkins.blog.posts.PostService;
import com.austincalkins.blog.posts.exceptions.DuplicatePostException;
import com.austincalkins.blog.posts.exceptions.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void createPost_willThrowExceptionIfPostWithSameTitleExists() {

        // setup
        Post postToCreate = new Post();
        postToCreate.setTitle("existing post title");

        Post existingPost = new Post();
        existingPost.setId(1L);
        existingPost.setTitle("existing post title");

        Mockito.when(postRepository.findPostByTitle(any())).thenReturn(Optional.of(existingPost));

        // test & verify
        assertThrows(DuplicatePostException.class, () -> this.postService.createPost(postToCreate));
    }

    @Test
    public void createPost_callsToSavePost() {
        // setup
        Post postToCreate = new Post();
        postToCreate.setTitle("new post title");

        // test
        this.postService.createPost(postToCreate);

        // verify
        Mockito.verify(this.postRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void createPost_willGenerateCreatedDateAndModifiedDateForPost() {

        // setup
        Post postToUpdate = new Post();
        postToUpdate.setTitle("new post title");

        Mockito.when(this.postRepository.save(any())).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            Post post = (Post) args[0];
            post.setId(1L);
            return post;
        });

        // test
        Post createdPost = this.postService.createPost(postToUpdate);

        // verify
        assertNotNull(createdPost);
        assertNotNull(createdPost.getCreatedDate());
        assertNotNull(createdPost.getModifiedDate());
    }

    @Test
    public void updatePost_callsToSavePost() {

        // setup
        Post postToUpdate = new Post(1L, "new title", LocalDate.now());

        Mockito.when(this.postRepository.findById(any())).thenReturn(Optional.of(
                new Post(1L, "old title", LocalDate.now())
        ));

        // test
        this.postService.updatePost(postToUpdate);

        // verify
        Mockito.verify(this.postRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updatePost_throwsExceptionWhenPostDoesNotExist() {

        // setup
        Post postToUpdate = new Post(1L, "updated title", LocalDate.of(2020, Month.JULY, 5));

        //test & verify
        assertThrows(PostNotFoundException.class, () -> postService.updatePost(postToUpdate));
    }

    @Test
    public void updatePost_willUpdateModifiedDate() {
        // setup
        LocalDate oldDateValue = LocalDate.of(2020, Month.JULY, 5);
        Post postToUpdate = new Post(1L, "updated title", oldDateValue);

        Mockito.when(this.postRepository.findById(any())).thenReturn(
                Optional.of(
                    new Post(1L, "old title", oldDateValue)
                )
        );

        Mockito.when(this.postRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        // test
        Post updatedPost = this.postService.updatePost(postToUpdate);

        // verify
        assertNotEquals(oldDateValue.toEpochDay(), updatedPost.getModifiedDate().toEpochDay());
    }

    @Test
    public void deletePost_throwsExceptionWhenPostDoesNotExist() {
        assertThrows(PostNotFoundException.class, () -> this.postService.deletePost(1L));
    }
}
