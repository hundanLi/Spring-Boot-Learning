package com.tcl.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/5 14:25
 */
@RestControllerAdvice(basePackages = "")
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        return e.toString();
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
