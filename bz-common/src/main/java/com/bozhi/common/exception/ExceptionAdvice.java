package com.bozhi.common.exception;

import com.bozhi.common.base.BuildResult;
import com.bozhi.common.base.MessageCode;
import com.bozhi.common.base.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        return BuildResult.buildFail(MessageCode.REQUEST_EXCEPTION);
    }
}
