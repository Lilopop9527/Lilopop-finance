package com.common.core.pojo;

public class LogMessage {
    private String from;
    private String data;
    private String method;
    private Integer status;

    private LogMessage(String from,String data,Integer status,String method){
        this.from = from;
        this.data = data;
        this.status = status;
        this.method = method;
    }

    public static LogMessage builder(String from,String data,Integer status,String method){
        return new LogMessage(from, data, status,method);
    }

    public String getFrom() {
        return from;
    }

    public String getData() {
        return data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMethod() {
        return method;
    }
}
