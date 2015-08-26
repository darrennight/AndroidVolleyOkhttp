package com.breadtrip.sdk.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.breadtrio.sdk.common.utils.LogUtils;
import com.breadtrip.sdk.http.tool.CodeError;
import com.breadtrip.sdk.http.tool.HttpQueue;
import com.breadtrip.sdk.http.tool.HttpResponseListener;

import java.util.Map;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.http
 * @Description: 网络请求管理类
 * @date 15/8/12 下午2:21
 */
public class HttpManager {

    /** The default socket timeout in milliseconds */
    public static final int DEFAULT_TIMEOUT_MS = 10000;

    /** The default number of retries */
    public static final int DEFAULT_MAX_RETRIES = 1;

    /** The default backoff multiplier */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

    private static HttpManager instance;

    private RequestQueue mRequestQueue;

    private Context mContext;

    private HttpManager() {
    }

    /**
     * 初始化httpqueue
     * @param context 上下文，建议application context
     * @param cachePath http缓存路径
     */
    public synchronized void initiate(Context context, String cachePath) {
        mContext = context;
        if (mRequestQueue == null) {
            mRequestQueue = HttpQueue.newRequestQueue(context, cachePath);
        }
    }

    /**
     * 获取单例
     * @return HttpManager
     */
    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 开始一个http请求
     * @param requestParams 请求参数
     * @param responseListener 回调接口
     * @param <T> model
     */
    public <T> void startRequest(final BaseRequestParams requestParams, final HttpResponseListener<T> responseListener) {

        Request<T> request = makeRequest(requestParams, responseListener);
        if (mRequestQueue != null) {
            mRequestQueue.add(request);
        } else {
            LogUtils.e("请求队列已经stop了");
        }
    }

    protected <T> HttpRequest<T> makeRequest(final BaseRequestParams requestParams, final HttpResponseListener<T> responseListener) {
        Response.Listener<T> listener = new Response.Listener<T>() {
            @Override
            public void onResponse(final T response) {
                if (responseListener != null) {
                    responseListener.onSuccess(response);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (responseListener != null) {
                    responseListener.onFailure(getErrorCode(error), getErrorString(error));
                }
            }
        };

        HttpRequest<T> request = new HttpRequest<>(requestParams, listener, errorListener);

        printRequest(request);

        if (!requestParams.isNeedRetry()) {
            request.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
        } else {
            request.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));
        }

        if (requestParams.getTag() != null) {
            request.setTag(requestParams.getTag());
        }

        return request;
    }


    /**
     * 根据tag结束请求
     *
     * @param tag 请求tag
     */
    public void cancelAll(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void cancelFileter(RequestQueue.RequestFilter filter) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(filter);
        }
    }

    /**
     * 停止请求队列
     */
    public synchronized void stop() {
        if (mRequestQueue != null) {
            mRequestQueue.stop();
            mRequestQueue = null;
        }

        if(mContext != null){
            mContext = null;
        }
    }


    private <T> void printRequest(HttpRequest<T> request) {
        if (LogUtils.isDebugable()) {
            LogUtils.d(HttpConfig.HTTP_TAG, "http请求： url = " + request.getUrl());
            try {
                Map<String, String> headers = request.getHeaders();
                if (headers != null && !headers.isEmpty()) {
                    StringBuilder header = new StringBuilder();
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        header.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                    }
                    String s = header.toString();
                    s = s.substring(0, s.length() - 1);
                    LogUtils.d(HttpConfig.HTTP_TAG, "http请求： headers : " + s);
                } else {
                    LogUtils.d(HttpConfig.HTTP_TAG, "http请求： headers 为空");
                }
            } catch (AuthFailureError e) {
                //
            }
            try {
                Map<String, String> params = request.getParams();
                if (params != null && !params.isEmpty()) {
                    StringBuilder param = new StringBuilder();
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        param.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                    }
                    String s = param.toString();
                    s = s.substring(0, s.length() - 1);
                    LogUtils.d(HttpConfig.HTTP_TAG, "http请求： params : " + s);
                } else {
                    LogUtils.d(HttpConfig.HTTP_TAG, "http请求： params 为空");
                }
            } catch (AuthFailureError e) {
                //
            }
        }
    }

    /**
     * 获取错误代码
     *
     * @param error 错误信息
     * @return 返回错误
     */
    private String getErrorCode(VolleyError error) {
        return error instanceof CodeError ? ((CodeError) error).getCode() : null;
    }

    /**
     * 获取错误信息
     *
     * @param error 错误信息
     * @return 返回字符串错误
     */
    private String getErrorString(VolleyError error) {
        String msg = mContext.getResources().getString(R.string.toast_error_network);
        if (error instanceof NoConnectionError) {
            msg = mContext.getResources().getString(R.string.http_error_connectfailed);
        } else if (error instanceof ParseError) {
            msg = mContext.getResources().getString(R.string.http_error_parsefailed);
        } else if (error instanceof ServerError) {
            msg = mContext.getResources().getString(R.string.http_error_servicefailed);
        } else if (error instanceof TimeoutError) {
            msg = mContext.getResources().getString(R.string.http_error_timeout);
        } else if (error instanceof CodeError) {
            if (TextUtils.isEmpty(error.getMessage())) {
                msg = error.getMessage();
            }
        }
        return msg;
    }

}
