package com.assessment.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ValidationFailureException extends RuntimeException {

    private static final long serialVersionUID = 654654564655L;

    public ValidationFailureException(String message) {
        super(message);
    }

}