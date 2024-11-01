package com.crio.starter.exception;

public class MemesAlreadyExistException extends RuntimeException {
    public MemesAlreadyExistException(String message) {
        super(message);
    }
    
}
