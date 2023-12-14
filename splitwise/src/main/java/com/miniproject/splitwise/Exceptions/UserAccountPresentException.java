package com.miniproject.splitwise.Exceptions;

public class UserAccountPresentException extends RuntimeException{
    public UserAccountPresentException(String message) {
        super(message);
    }
}
