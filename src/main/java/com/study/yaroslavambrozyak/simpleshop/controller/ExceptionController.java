package com.study.yaroslavambrozyak.simpleshop.controller;

import com.study.yaroslavambrozyak.simpleshop.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e){
        logger.error("Not Found",e);
        return "404";
    }

    @ExceptionHandler(Throwable.class)
    public String handleException(Throwable t){
        logger.error("ERR",t);
        return "500";
    }
}
