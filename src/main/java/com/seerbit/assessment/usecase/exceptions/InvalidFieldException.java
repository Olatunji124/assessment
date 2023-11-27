package com.seerbit.assessment.usecase.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String message){
        super(message);
    }
}
