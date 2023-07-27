package com.common.core.exception;

/**
 *断言处理类，用于抛出自定义异常
 */
public class Asserts {
    public static void fail(String message){
        throw new GlobalException(message);
    }
    public static void fail(Integer code,String message){
        throw new GlobalException(code,message);
    }
}
