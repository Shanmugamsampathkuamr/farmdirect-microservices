package com.location_service.exception;

public class LocationNotFoundException extends RuntimeException  {
    public LocationNotFoundException(String message){
        super(message);
    }
}
