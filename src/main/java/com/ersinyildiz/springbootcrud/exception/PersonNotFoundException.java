package com.ersinyildiz.springbootcrud.exception;

import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Generated //Ignore for test
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public PersonNotFoundException(String message) {
        super(message);
    }

}
