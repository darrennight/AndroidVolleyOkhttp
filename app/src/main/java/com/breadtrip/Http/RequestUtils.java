package com.breadtrip.Http;

import android.os.Build;
import android.text.TextUtils;

import com.breadtrio.sdk.common.utils.PackageUtils;
import com.breadtrip.application.BreadTripApplication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * 请求管理类
 *
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Title: RequestUtils.java
 * @Description:
 * @date 2015年6月11日 上午10:09:10
 */
public class RequestUtils {

    private static final String KEY_USER_AGENT = "User-Agent";
    private static String USER_AGENT = null;

    /**
     * 所有请求通用header参数
     */
    private static Map<String, String> COMMON_HEADER_PARAMS;

    /**
     * 获取userAgent
     *
     * @return String
     */
    public static String getUserAgent() {
        if (TextUtils.isEmpty(USER_AGENT)) {
            USER_AGENT = "BreadTrip/android/" + PackageUtils.getVersionName(BreadTripApplication.getInstance()) + "/"
                    + Locale.getDefault().getLanguage() + " (android OS" + Build.VERSION.RELEASE + ") "
                    + (TextUtils.isEmpty(Build.DEVICE) ? "UNKOWN" : Build.DEVICE) + " " + BreadTripApplication.mapType
                    + " (" + Build.MODEL + "," + Build.BRAND + ")";
        }
        return USER_AGENT;
    }

    /**
     * 获取通用header参数
     *
     * @return
     */
    public static Map<String, String> getCommonHeaderParams() {
        if (COMMON_HEADER_PARAMS == null) {
            COMMON_HEADER_PARAMS = new HashMap<String, String>();
            // UserAgent
            COMMON_HEADER_PARAMS.put(KEY_USER_AGENT, getUserAgent());
            // 其他参数
        }
        return COMMON_HEADER_PARAMS;
    }

    /**
     * http请求通用参数
     *
     * @return
     */
    public static Map<String, String> getCommonEntityParams() {
        //通用参数
        return Collections.emptyMap();
    }

}
