package com.tenPines.restAPI;

import org.springframework.validation.ObjectError;

import java.util.List;

public class RestfulException extends Exception {
    public List<ObjectError> errors;

    public RestfulException(List<ObjectError> errors) {
        this.errors = errors;
    }
}
