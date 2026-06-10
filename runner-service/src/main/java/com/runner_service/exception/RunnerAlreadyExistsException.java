package com.runner_service.exception;

public class RunnerAlreadyExistsException extends RuntimeException{
    public RunnerAlreadyExistsException(String message){
        super(message);
    }
}
