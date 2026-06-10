package com.farmer_service.exception;

public class FarmerNotFoundException extends RuntimeException {
    public FarmerNotFoundException(String message){
        super(message);
    }

}
