package com.example.lib_base.mvvm.exception;

/**
 * <pre>
 *     author : yule
 *     time   : 2018/06/28
 *     desc   : 网络层公共异常
 * </pre>
 */

public class CommonException extends Exception {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CommonException() {
    }
    public CommonException(int code) {
        this.code = code;
    }

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }


}
