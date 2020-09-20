package me.backendj.lboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostsNotFoundException extends RuntimeException {
    public PostsNotFoundException() {}
    public PostsNotFoundException(String message) {
        super(message);
    }
}
