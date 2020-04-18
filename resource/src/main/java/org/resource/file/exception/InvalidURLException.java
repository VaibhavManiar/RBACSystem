package org.resource.file.exception;

public class InvalidURLException extends RuntimeException {
    public InvalidURLException(String message) {
        super(message);
    }

    public InvalidURLException(String message, Throwable th) {
        super(message, th);
    }
}