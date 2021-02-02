package com.ersinyildiz.springbootcrud.exception;


import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Generated //Ignore for test
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonNotValidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PersonNotValidException(String message){
        super(message);
    }
}
