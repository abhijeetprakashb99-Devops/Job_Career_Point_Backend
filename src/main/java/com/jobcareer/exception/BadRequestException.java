package com.jobcareer.exception;
public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) { super(msg); }
}