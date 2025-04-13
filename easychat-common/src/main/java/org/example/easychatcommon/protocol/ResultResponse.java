package org.example.easychatcommon.protocol;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultResponse<T> {
    boolean success = true;
    int code = 10000;
    String message;
    T data;

    public ResultResponse(ResultCode resultCode, T data) {
        this.success = resultCode.isSuccess();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public ResultResponse(ResultCode resultCode) {
        this.success = resultCode.isSuccess();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public ResultResponse(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static <T> ResultResponse<T> success(T data) {
        return new ResultResponse<>(CommonCode.SUCCESS, data);
    }

    public static <T> ResultResponse<T> success() {
        return new ResultResponse<>(CommonCode.SUCCESS);
    }
    //失败了肯定没有data
    public static <T> ResultResponse<T> fail() {
        return new ResultResponse<>(CommonCode.FAIL);
    }
    public static <T> ResultResponse<T> error(ResultCode resultCode) {
        return new ResultResponse<>(resultCode);
    }
}
