package com.example.lib_base.mvvm.exception;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * <pre>
 *     author : yule
 *     time   : 2018/06/28
 *     desc   : 异常处理器可以处理网络底层网络出现的错误，也可以处理服务端公共状态码
 * </pre>
 */

public class ExceptionHandler {

    public static final int UNKNOWN = -1;
    public static final int IGNORE = -2;
    public static final int JSON_PARSE_ERROR = 999;
    public static final int EXCEPTION1 = 10000;
    public static final int EXCEPTION2 = EXCEPTION1 + 1;
    public static final int EXCEPTION3 = EXCEPTION2 + 1;
    public static final int EXCEPTION4 = EXCEPTION3 + 1;
    public static final int EXCEPTION5 = EXCEPTION4 + 1;
    public static final int EXCEPTION6 = EXCEPTION5 + 1;

    public static String handleException(Exception exception) {
        if (exception instanceof CommonException) {
            return handleCommonException((CommonException) exception);
        } else if (exception != null) {
            return exception.getMessage();
        }else {
            return "未知错误";
        }
    }

    public static String handleCommonException(CommonException exception) {
        String msg = exception.getMsg();
        switch (exception.getCode()) {
            case 403:
            case 422:
                msg = "请重新登录！";
                break;
            case 500:
                msg = "服务器无响应，请稍后再试！";
                break;
            case 501:
                msg = "请求参数出错啦！";
                break;
            case EXCEPTION1:
                msg = "连接失败，请检查网络";
                break;
            case EXCEPTION2:
                msg = NetConstants.SERVER_OFFLINE;
                break;
            case EXCEPTION3:
                msg = "暂无数据";
                break;
            case EXCEPTION4:
                msg = "淘宝订单拉取失败";
                break;
            case EXCEPTION5:
                msg = "时间不准确,证书无效,请调整为正确的时间";
                break;
            case EXCEPTION6://请求被取消
                msg = "";
                break;
            case JSON_PARSE_ERROR:
                msg = "数据格式有误";
                break;
            case UNKNOWN:
                if (TextUtils.isEmpty(msg)) {
                    msg = "未知错误";
                }
                break;
            default:
                msg = exception.getMessage();
                break;
        }
        return msg;
    }

    public static String handleSuccessException(CommonException exception) {

        String msg = exception.getMsg();
        switch (exception.getCode()) {
            case NetConstants.CODE_TOKEN_EXPIRED:
                //401，token失效，需要重新获取
            case NetConstants.CODE_TOKEN_INVALID:
                //403,token失效，需要重新登录(403统一处理,弹框提示重新登录)
                msg = "";
                break;
            case NetConstants.CODE_TOKEN_ERROR:
                //422，token获取失败
                break;
            case NetConstants.CODE_SERVER_ERROR:
                //500,服务器错误
                break;
            default:
                break;
        }
        return msg;
    }

    public static String handleFailException(Throwable e) {
        e.printStackTrace();
        CommonException exception;
        //成功情况下返回错误码处理
        if (e instanceof CommonException) {
            exception = (CommonException) e;
            return ExceptionHandler.handleSuccessException(exception);
        }

        //失败情况下处理
        if (e instanceof HttpException) {
            //http 异常
            HttpException httpException = (HttpException) e;
            exception = new CommonException(httpException.code());
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //Json解析异常
            exception = new CommonException(ExceptionHandler.JSON_PARSE_ERROR);
        } else if (e instanceof UnknownHostException || e instanceof NetworkErrorException || e instanceof ConnectException) {
            exception = new CommonException(ExceptionHandler.EXCEPTION1, e.getMessage());
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            exception = new CommonException(ExceptionHandler.EXCEPTION2, e.getMessage());
        } else { //其他异常
            exception = new CommonException(ExceptionHandler.UNKNOWN, e.getMessage());
        }
        //底层直接toast提示
        return ExceptionHandler.handleException(exception);
    }

    public static CommonException convertCommonException(Throwable e) {
        CommonException exception;

        if (e instanceof HttpException) {
            //http 异常
            HttpException httpException = (HttpException) e;
            exception = new CommonException(httpException.code(), httpException.getMessage());
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //Json解析异常
            exception = new CommonException(ExceptionHandler.JSON_PARSE_ERROR);
        } else if (e instanceof UnknownHostException || e instanceof NetworkErrorException || e instanceof ConnectException) {
            exception = new CommonException(ExceptionHandler.EXCEPTION1, e.getMessage());
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            exception = new CommonException(ExceptionHandler.EXCEPTION2, e.getMessage());
        } else if (e instanceof SSLHandshakeException) {
            exception = new CommonException(ExceptionHandler.EXCEPTION5, e.getMessage());
        } else { //其他异常
            exception = new CommonException(ExceptionHandler.UNKNOWN, e.getMessage());
        }
        return exception;
    }
}
