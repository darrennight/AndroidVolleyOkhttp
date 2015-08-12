package com.breadtrio.sdk.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * 避免出现多个toast显示的case
 * 
 * @author xiangyutian 
 *         create at 2014-4-16 上午11:19:02
 */
public class ToastUtils {

    private static Toast toast;
    private static int version = android.os.Build.VERSION.SDK_INT;
    private static final int MAX_NEED_CANCEL_VERSION = 10;

    /**
     * 显示toast,Toast.LENGTH_SHORT
     * 
     * @param context
     * @param resId
     */
    public static void ToastShort(Context context, int resId) {
        initToast(context, resId);
        toast.show();
    }

    /**
     * 显示toast,Toast.LENGTH_SHORT
     * 
     * @param context
     * @param text
     */
    public static void ToastShort(Context context, String text) {
        initToast(context, text);
        toast.show();
    }

    /**
     * 显示toast,Toast.LENGTH_LONG
     * 
     * @param context
     * @param resId
     */
    public static void ToastLong(Context context, int resId) {
        initToast(context, resId);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 显示toast,Toast.LENGTH_LONG
     * 
     * @param context
     * @param text
     */
    public static void ToastLong(Context context, String text) {
        initToast(context, text);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 隐藏toast
     */
    public static void hide() {
        if (toast == null) {
            return;
        }
        toast.cancel();
    }

    /**
     * 初始化Toast ，替换为统一样式， R.layout.toast
     * 
     * @param resId
     *            需要显示的String 资源ID
     */
    private static void initToast(Context context, int resId) {
        initToast(context, context.getResources().getString(resId));
    }

    /**
     * 初始化Toast ，替换为统一样式， R.layout.toast
     * 
     * @param text
     *            需要显示的Toast 文字
     */
    @SuppressLint("ShowToast")
    private static void initToast(Context context, String text) {
        if (toast == null) {
            if (context != null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            }
        }
        toast.setText(text);
        if (version <= MAX_NEED_CANCEL_VERSION) {
            toast.cancel();
        }
    }

}
