package com.breadtrip.Http;


import com.android.volley.Request;
import com.breadtrip.sdk.http.BaseRequestParams;

import java.util.Map;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.http
 * @Description: 参数类
 * @date 15/8/11 上午10:40
 */
public class RequestParams extends BaseRequestParams {

    public RequestParams(String url, Object parseTag) {
        super(url, parseTag);
        init();
    }

    public RequestParams(String url, Object parseTag, int method) {
        super(url, parseTag, method);
        init();
    }

    private void init() {
        Map<String, String> headers = RequestUtils.getCommonHeaderParams();
        if (headers != null && headers.size() > 0) {
            addHeaderAll(headers);
        }

        Map<String, String> entities = RequestUtils.getCommonEntityParams();
        if (entities != null && entities.size() > 0) {
            addEntityAll(entities);
        }
    }

    /**
     * Builder构造类
     *
     * @author jiwei@breadtrip.com
     * @version V1.0
     * @Title: RequestParams.java
     * @Description:
     * @date 2015年6月8日 下午7:24:01
     */
    public static class Builder {

        private RequestParams params;

        public Builder(String url, Object parseTag) {
            this(url, parseTag, Request.Method.GET);
        }

        public Builder(String url, Object parseTag, int method) {
            params = new RequestParams(url, parseTag, method);
        }

        public Builder setMethod(int method) {
            params.setMethod(method);
            return this;
        }

        public Builder addQueryParam(String key, long value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        public Builder addQueryParam(String key, float value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        public Builder addQueryParam(String key, double value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        public Builder addQueryParam(String key, int value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        public Builder addQueryParam(String key, boolean value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        public Builder addQueryParam(String key, String value) {
            params.addQueryParam(key, value);
            return this;
        }

        public Builder addHeaderParam(String key, long value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        public Builder addHeaderParam(String key, float value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        public Builder addHeaderParam(String key, double value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        public Builder addHeaderParam(String key, int value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        public Builder addHeaderParam(String key, boolean value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        public Builder addHeaderParam(String key, String value) {
            params.addHeaderParam(key, value);
            return this;
        }

        public Builder addEntityStringParam(String key, long value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        public Builder addEntityStringParam(String key, float value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        public Builder addEntityStringParam(String key, double value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        public Builder addEntityStringParam(String key, int value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        public Builder addEntityStringParam(String key, boolean value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        public Builder addEntityStringParam(String key, String value) {
            params.addEntityStringParam(key, value);
            return this;
        }

        public Builder addFileParams(String key, String filePath) {
            params.addFileParams(key, filePath);
            return this;
        }

        public Builder setSecret(boolean secret) {
            params.setSecret(secret);
            return this;
        }

        public Builder setNeedRetry(boolean isNeedRetry) {
            params.setNeedRetry(isNeedRetry);
            return this;
        }

        public Builder setTag(Object tag) {
            params.setTag(tag);
            return this;
        }

        public RequestParams create() {
            return params;
        }
    }
}
