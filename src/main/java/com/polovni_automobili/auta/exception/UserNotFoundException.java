package com.polovni_automobili.auta.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1226439803994500725L;

    public UserNotFoundException(String msg){
        super(msg);
    }
}
