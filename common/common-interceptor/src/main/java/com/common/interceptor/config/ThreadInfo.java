package com.common.interceptor.config;

import java.util.List;
import java.util.Map;

/**
 * @author: Lilopop
 * @description:
 */
public class ThreadInfo {
    private static final ThreadLocal<Map<String,Object>> THREAD_INFO = new ThreadLocal<>();

    public ThreadInfo(){
    }

    public static void set(Map<String,Object> value){
        THREAD_INFO.set(value);
    }

    public static Map<String,Object> get(){
        return THREAD_INFO.get();
    }

    public static void remove(){
        THREAD_INFO.remove();
    }
}
