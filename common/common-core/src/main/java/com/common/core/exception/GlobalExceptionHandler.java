package com.common.core.exception;

import com.common.core.pojo.CommonData;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 返回自定义异常处理
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public CommonData handle(GlobalException exception){
        return CommonData.fail(exception.getCode(),exception.getMessage(),null);
    }

    /**
     * 参数校验失败异常处理
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonData handleValidException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonData.validateFailed(message);
    }

    /**
     * 绑定错误异常处理方法
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonData handleValidException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonData.validateFailed(message);
    }

    /**
     * 方法参数无效异常处理
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonData handleValidException(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        StringBuffer sb = new StringBuffer();
        for (ConstraintViolation<?> item: constraintViolations){
            sb.append(item.getMessage()).append(";");
        }
        return CommonData.validateFailed(sb.toString());
    }
}
