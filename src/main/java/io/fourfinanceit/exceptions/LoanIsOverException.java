package io.fourfinanceit.exceptions;

/**
 * Created by cons on 15/12/16.
 */
public class LoanIsOverException extends ValidationException {

    public LoanIsOverException(String message) {
        super(message);
    }
}
