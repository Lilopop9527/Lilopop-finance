package com.common.core.pojo;

public class CommonData<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonData() {
    }

    public CommonData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public static <T> CommonData<T> success(T data){
        return new CommonData<>(CommonEnums.OK.getKey(), CommonEnums.OK.getValue(),data);
    }
    public static <T> CommonData<T> success(String message,T data){
        return new CommonData<>(CommonEnums.OK.getKey(), message,data);
    }
    public static <T> CommonData<T> success(String message){
        return new CommonData<>(CommonEnums.OK.getKey(), message,null);
    }
    public static <T> CommonData<T> success(Integer code,String message,T data){
        return new CommonData<>(code,message,data);
    }
    public static <T> CommonData<T> fail(T data){
        return new CommonData<>(CommonEnums.ERROR.getKey(), CommonEnums.ERROR.getValue(), data);
    }
    public static <T> CommonData<T> fail(String message,T data){
        return new CommonData<>(CommonEnums.ERROR.getKey(),message, data);
    }
    public static <T> CommonData<T> fail(String message){
        return new CommonData<>(CommonEnums.ERROR.getKey(),message, null);
    }
    public static <T> CommonData<T> fail(Integer code,String message,T data){
        return new CommonData<>(code,message,data);
    }
    public static <T> CommonData<T> validateFailed(T data){
        return fail(data);
    }
    public static <T> CommonData<T> validateFailed(String message){
        return fail(message);
    }
    public static <T> CommonData<T> validateFailed(){
        return fail(CommonEnums.UNFOUND.getKey(),CommonEnums.UNFOUND.getValue(),null);
    }
    public static <T> CommonData<T> unauthorized(T data){
        return fail(CommonEnums.UNAUTHORIZED.getKey(), CommonEnums.UNAUTHORIZED.getValue(), data);
    }
    public static <T> CommonData<T> forbidden(T data){
        return fail(CommonEnums.FORBIDDEN.getKey(),CommonEnums.FORBIDDEN.getValue(), data);
    }
}
