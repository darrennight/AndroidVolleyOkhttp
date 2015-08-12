package com.breadtrio.sdk.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * DisplayUtils
 * <ul>
 * <li>{@link #getDisplay(Context)}获取显示设备</li>
 * <li>{@link #getScreenWidth(Context)}获取屏幕宽度</li>
 * <li>{@link #getScreenHeight(Context)} 获取屏幕高度大小</li>
 * <li>{@link #dipToPx(Context, float)}dip转换为px</li>
 * <li>{@link #pxToDip(Context, int)}px转换为dip</li>
 * <li>{@link #getDensityDpi(Context)}获取屏幕密度</li>
 * </ul>
 *
 * @author jiwei
 * @since 2013-11-18
 */
public class DisplayUtils {
    private static final String TAG = DisplayUtils.class.getSimpleName();

    /**
     * 获取显示设备参数
     *
     * @param context 上下文
     * @return Display
     */
    public static Display getDisplay(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    /**
     * 获取屏幕宽度大小，单位px
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        Display display = getDisplay(context);
        return display.getWidth();
    }

    /**
     * 获取屏幕高度大小，单位px
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getScreenHeight(Context context) {
        Display display = getDisplay(context);
        return display.getHeight();
    }

    /**
     * dip转换为px
     *
     * @param context 上下文
     * @param dip     dp
     * @return px
     */
    public static int dipToPx(Context context, float dip) {
        return (int) (dip * getDisplayMetrics(context).density + 0.5f);
    }

    /**
     * px转换为dip
     *
     * @param context 上下文
     * @param px      px
     * @return dp
     */
    public static int pxToDip(Context context, int px) {
        return (int) (px / getDisplayMetrics(context).density + 0.5f);
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        Display display = getDisplay(context);
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm;
    }

    /**
     * 获取屏幕密度
     *
     * @param context 上下文
     * @return 屏幕密度
     */
    public static int getDensityDpi(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.densityDpi;
    }

    /**
     * 获得状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
}
