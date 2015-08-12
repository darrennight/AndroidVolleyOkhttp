package com.breadtrio.sdk.common.utils;

import android.util.Log;

import java.util.Locale;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.common.utils
 * @Description: 日志类
 * @date 15/8/6 下午2:22
 */
public class LogUtils {
    private static final String TAG = "LogUtils";

    private static boolean ENABLE_LOG = false;

    public static boolean isDebugable() {
        return ENABLE_LOG;
    }

    /**
     * 开启/关闭调试模式
     *
     * @param enabled
     */
    public static void enableDebugMode(boolean enabled) {
        ENABLE_LOG = enabled;
    }

    /**
     * 打印debug日志
     * <p/>
     * s * @param msg
     */
    public static void d(String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.d(result[0], result[1]);
        }
    }

    /**
     * 打印debug日志
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.d(tag, result[1]);
        }
    }

    /**
     * 打印debug日志
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static final void d(String tag, String msg, Throwable tr) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.d(tag, result[1], tr);
        }
    }

    /**
     * 打印info日志
     *
     * @param msg
     */
    public static void i(String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.i(result[0], result[1]);
        }
    }

    /**
     * 打印info日志
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.i(tag, result[1]);
        }
    }

    /**
     * 打印info日志
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.i(tag, result[1], tr);
        }
    }

    public static final void v(String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.v(result[0], result[1]);
        }
    }

    public static void v(String tag, String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.v(tag, result[1]);
        }
    }

    /**
     * 打印warning日志
     *
     * @param msg
     */
    public static void w(String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.w(result[0], result[1]);
        }
    }

    /**
     * 打印warning日志
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.w(tag, result[1]);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (ENABLE_LOG) {
            Log.w(tag, tr);
        }
    }

    /**
     * 打印warning日志
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.w(tag, result[1], tr);
        }
    }

    /**
     * 打印error日志
     *
     * @param msg
     */
    public static void e(String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.e(result[0], result[1]);
        }
    }

    /**
     * 打印error日志
     *
     * @param e
     */
    public static void e(Exception e) {
        e(e.toString());
    }

    /**
     * 打印error日志
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.e(tag, result[1]);
        }
    }

    /**
     * 打印error日志
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (ENABLE_LOG) {
            String[] result = getMessage(msg);
            Log.e(tag, result[1], tr);
        }
    }

    /**
     * 打印对象
     *
     * @param obj
     */
    public static void p(Object obj) {
        if (ENABLE_LOG) {
            System.out.println(obj);
        }
    }

    /**
     * Get calling class and format messages
     */
    private static String[] getMessage(String msg) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String caller = "<unknown>";
        String callingClass = "<unknown>";
        int line = -1;
        // Walk up the stack looking for the first caller It will be at least two frames up, so start there.
        callingClass = trace[4].getClassName();
        line = trace[4].getLineNumber();
        callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
        callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);
        caller = callingClass + "." + trace[4].getMethodName();
        return new String[]{callingClass, String.format(Locale.US, "[%s:%d] %s", caller, line, msg)};
    }
}
