package com.ecommerce.exception;

public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException(String msg){
        super(msg);
    }
}
