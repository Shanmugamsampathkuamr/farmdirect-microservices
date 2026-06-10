package com.farmer_service.exception;

public class FarmerAlreadyExistsException extends RuntimeException {
    public FarmerAlreadyExistsException(String message){
        super(message);
    }

}
