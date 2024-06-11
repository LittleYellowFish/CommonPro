package com.example.lib_base.mvvm.exception;

/**
 * <pre>
 *     author : lilinjie
 *     time   : 2018/06/28
 *     desc   : 网络层常量类
 * </pre>
 */
public class NetConstants {


    public static final int CODE_TOKEN_EXPIRED = 401;                 //token失效 需要请求刷新token接口获取新的token
    public static final int CODE_TOKEN_INVALID = 403;                 //token过期，重新登录
    public static final int CODE_TOKEN_ERROR = 422;                   //token获取失败
    public static final int CODE_ACCOUNT_BLOCKED = 21001;                   //账号被封禁
    public static final int CODE_SERVER_ERROR = 500;                  //服务器出错

    public static String SERVER_OFFLINE = "请求超时";//"当前网络差，请确认网络情况哦";//服务器响应超时
}
