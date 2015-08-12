package com.breadtrip.sdk.http.tool;


import android.support.annotation.NonNull;

/**
 * http回调函数
 *
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Title: HttpResponseListener.java
 * @Description:
 * @date 2015年3月23日 上午10:30:41
 */
public interface HttpResponseListener<T> {

    /**
     * Called when a response is received
     *
     * @param result T对象
     */
    void onSuccess(@NonNull T result);

    /**
     * Called when failure
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    void onFailure(String errorCode, String errorMsg);
}
