package com.usm4.controller;

import com.usm4.exception.ResourcesNotFound;
import com.usm4.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@ControllerAdvice    // This class is capable of receiving the exception wherever it occurs in the project that exception come here this exception handler class Automatically
public class ExceptionHandlerClass {

    @ExceptionHandler(ResourcesNotFound.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(
            ResourcesNotFound resourcesNotFound,
            WebRequest webRequest
    ){

        ErrorDetails errorDetails = new ErrorDetails(resourcesNotFound.getMessage(),new Date(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(
            Exception exception,
            WebRequest webRequest
    ){

        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(),new Date(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



//I created controllerAdvice Class and in that created exception  handler method so that any exception will occur
//in the project this class is responsible for handle this exception.