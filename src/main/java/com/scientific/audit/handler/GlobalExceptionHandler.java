package com.scientific.audit.handler;


import com.scientific.audit.common.model.base.Result;
import com.scientific.audit.common.model.base.ResultCode;
import com.scientific.audit.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));

        return Result.build(ResultCode.BAD_REQUEST, message);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IOException.class)
    public Result handleIOException(IOException e) {
        log.error(e.getMessage());
        return Result.build(ResultCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = CustomException.class)
    public Result handleCustomException(CustomException e) {
        log.debug(e.getResultCode().getMsg());
        return Result.build(e.getResultCode());
    }
}
