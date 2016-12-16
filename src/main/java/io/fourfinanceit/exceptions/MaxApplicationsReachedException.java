package io.fourfinanceit.exceptions;

/**
 * Created by cons on 15/12/16.
 */
public class MaxApplicationsReachedException extends ValidationException {

    public MaxApplicationsReachedException(String message) {
        super(message);
    }
}
