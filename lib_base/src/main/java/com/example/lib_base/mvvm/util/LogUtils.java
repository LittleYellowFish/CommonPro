package com.example.lib_base.mvvm.util;

import android.util.Log;

import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * <pre>
 *    创建者：LLJ
 *    创建时间：2018/04/10 13:29
 *    描述：日志工具类
 * </pre>
 */
public class LogUtils {

    /**
     * 控制log日志的输出
     */

    public static void setIsLog(boolean isLog) {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isLog;
            }
        });
    }

    public static void v(String tag, String msg) {
        Logger.t(tag).v(msg);
    }

    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    public static void w(String tag, String msg) {
        Logger.t(tag).w(msg);
    }

    public static void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }


    public static void maxDebug(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.d(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.d(tag, msg);
    }
}