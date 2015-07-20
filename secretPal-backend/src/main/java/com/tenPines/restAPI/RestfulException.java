package com.tenPines.restAPI;

import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class RestfulException extends Exception {
    public Number code = 100;
    public List<ObjectError> errors;

    public RestfulException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors.addAll(errors.stream().collect(Collectors.toList()));
    }

    public Number getCode() {
        return code;
    }

    public void setCode(Number code) {
        this.code = code;
    }

}
