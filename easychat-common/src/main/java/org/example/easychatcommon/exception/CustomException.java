package org.example.easychatcommon.exception;

import org.example.easychatcommon.protocol.ResultCode;

public class CustomException extends RuntimeException {
    private final ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super("错误代码:"+resultCode.getCode()+"错误信息:"+resultCode.getMessage());
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode() {return  this.resultCode;}
}
