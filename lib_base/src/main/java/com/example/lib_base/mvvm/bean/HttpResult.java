package com.example.lib_base.mvvm.bean;

import android.text.TextUtils;

/**
 * @auther leyu
 * @date 2018/5/10 09:54
 * @desc
 */
public class HttpResult<T> {


    public String code;
    public String message;
    public T data;

    public boolean success() {
        return TextUtils.equals("0", code);
    }
}
