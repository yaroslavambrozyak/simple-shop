package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final String NOT_FOUND_VIEW = "404";
    private final String SERVER_ERR_VIEW = "500";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e){
        logger.error("Not Found",e);
        return NOT_FOUND_VIEW;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Throwable t){
        logger.error("ERR",t);
        return SERVER_ERR_VIEW;
    }
}
