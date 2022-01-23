package com.austincalkins.blog.posts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatePostException extends IllegalStateException {

    public DuplicatePostException() {
        super();
    }
    public DuplicatePostException(String message) {
        super(message);
    }

}
