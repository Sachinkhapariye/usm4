package com.usm4.exception;

public class ResourcesNotFound extends RuntimeException{
    public ResourcesNotFound(String message){
        super(message);
    }
}
