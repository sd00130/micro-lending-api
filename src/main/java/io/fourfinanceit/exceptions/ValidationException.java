package io.fourfinanceit.exceptions;

/**
 * Created by cons on 16/12/16.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
