package com.common.core.exception;

public class GlobalException extends RuntimeException{
    private Integer code;
    private String message;

    public GlobalException(Integer code,String message){
        super(message);
        this.code = code;
        this.message = message;
    }
    public GlobalException(String message){
        super(message);
    }
    public GlobalException(Throwable throwable){
        super(throwable);
    }
    public GlobalException(String message,Throwable throwable){
        super(message,throwable);
    }
    public Integer getCode(){
        return this.code;
    }
}
