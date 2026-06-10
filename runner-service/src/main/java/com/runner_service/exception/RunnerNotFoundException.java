package com.runner_service.exception;

public class RunnerNotFoundException extends RuntimeException{
    public RunnerNotFoundException(String message){
        super(message);
    }
}
