package com.scientific.audit.exception;

import com.scientific.audit.common.model.base.ResultCode;
import lombok.Getter;
import lombok.Setter;

public class CustomException extends RuntimeException{

    @Getter
    @Setter
    private ResultCode resultCode;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
