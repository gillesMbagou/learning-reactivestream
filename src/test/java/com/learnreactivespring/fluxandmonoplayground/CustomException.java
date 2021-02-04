package com.learnreactivespring.fluxandmonoplayground;

import lombok.Getter;

@Getter
public class CustomException extends Throwable {
    private final String message;

    public CustomException(Throwable e) {
        this.message = e.getMessage();
    }
}
