package com.assessment.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TransferMoneyTransactionFailureException extends RuntimeException {

    private static final long serialVersionUID = 654654564651L;

    public TransferMoneyTransactionFailureException(String message) {
        super(message);
    }

}
