package com.assessment.wallet.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(),"Oops! Something went wrong",request.getDescription(false));
		return new ResponseEntity(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(AmountNotValidException.class)
	public final ResponseEntity<Object> handleAmountNotValidException(AmountNotValidException ex, WebRequest request){
		ExceptionResponse exceptionResponse =
				new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(TransferMoneyTransactionFailureException.class)
	public final ResponseEntity<Object> handleTransferMoneyTransactionFailureException(TransferMoneyTransactionFailureException ex, WebRequest request){
		ExceptionResponse exceptionResponse =
				new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ValidationFailureException.class)
	public final ResponseEntity<Object> handleValidationFailureException(ValidationFailureException ex, WebRequest request){
		ExceptionResponse exceptionResponse =
				new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
