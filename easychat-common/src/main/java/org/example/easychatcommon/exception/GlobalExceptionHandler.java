package org.example.easychatcommon.exception;

import com.google.common.collect.ImmutableMap;
import org.example.easychatcommon.protocol.CommonCode;
import org.example.easychatcommon.protocol.ResultCode;
import org.example.easychatcommon.protocol.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTION_MAP;
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultResponse<Object> handleException(Exception e) {
        logger.error("catch exception:{}\r\n",e.getMessage(), e);
        if(e instanceof CustomException) {
            CustomException customException = (CustomException) e;
            ResultCode resultCode =customException.getResultCode();
            return ResultResponse.error(resultCode);
        }

        if(EXCEPTION_MAP ==null){
            EXCEPTION_MAP = builder.build();
        }
        final ResultCode resultCode = EXCEPTION_MAP.get(e.getClass());
        if(resultCode !=null){
            return ResultResponse.error(resultCode);
        }
        return ResultResponse.error(CommonCode.UNKNOWN_ERROR);
    }
}
