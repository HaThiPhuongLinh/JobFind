package com.jobfind.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MethodNotAllowedException extends RuntimeException {
    private int errorCode = 405;

    public MethodNotAllowedException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public MethodNotAllowedException(String message){
        super(message);
    }
}
