package com.breadtrip.sdk.http;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.breadtrio.sdk.common.utils.LogUtils;
import com.breadtrip.sdk.http.tool.CodeError;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.http
 * @Description: http请求类
 * @date 15/8/12 下午2:27
 */
public class HttpRequest<T> extends Request<T> {

    private static final String TAG = HttpRequest.class.getName();

    private static final String KEY_SIGN = "sign";

    private final Response.Listener<T> mListener;

    private BaseRequestParams requestParams;

    /**
     * Creates a new request with the given method.
     *
     * @param requestParams 参数
     * @param listener 争取回调
     * @param errorListener 错误回调
     */
    public HttpRequest(BaseRequestParams requestParams, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(requestParams.getMethod(), requestParams.getUrlWithQueryString(), errorListener);
        mListener = listener;
        this.requestParams = requestParams;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        LogUtils.d("HttpTask", "http错误：" + error.toString());
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        if (mListener == null) {
            // 不需要回调，不解析
            return Response.success(null, null);
        }

        String jsonString;
        try {
            jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            jsonString = new String(response.data);
        }

        IPreProcessor preProcessor = requestParams.getPreProcessor();
        if (preProcessor != null) {
            jsonString = preProcessor.doPreProcess(jsonString);
        }

        LogUtils.d("HttpTask", jsonString);

        Object object = JsonParser.parseJson(requestParams.getParseTag(), jsonString);
        if (object != null) {
            JsonBaseModel result = (JsonBaseModel) object;
            String code = result.getStatus();
            Object data = result.getData();
            if (!TextUtils.isEmpty(code) && HttpConfig.SUCCESS_CODE.equals(code) && data != null) {
                try {
                    IPostProcessor postProcessor = requestParams.getPostProcessor();
                    if (postProcessor != null) {
                        data = postProcessor.doPostProcess(data);
                    }
                    @SuppressWarnings("unchecked")
                    T t = (T) data;
                    return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
                } catch (ClassCastException e) {
                    // 解析出来类型不符
                    return Response.error(new ParseError());
                }
            } else {
                String message = result.getMessage();
                return Response.error(new CodeError(code, message));
            }
        } else {
            return Response.error(new ParseError());
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return requestParams.getHeaderParams();
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> requestEntityParams = requestParams.getEntityStringParams();
        if (requestEntityParams != null && requestEntityParams.size() > 0 && requestParams.isSecret()) {
            return ParamsSecreter.getSecretParams(requestParams.getEntityStringParams());
        } else {
            return requestEntityParams;
        }
    }

}
