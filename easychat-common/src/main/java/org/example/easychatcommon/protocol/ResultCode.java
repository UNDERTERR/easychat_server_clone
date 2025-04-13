package org.example.easychatcommon.protocol;

public interface ResultCode {
    boolean isSuccess();
    int getCode();
    String getMessage();
}
