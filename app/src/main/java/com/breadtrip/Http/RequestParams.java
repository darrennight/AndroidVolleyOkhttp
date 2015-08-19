package com.breadtrip.Http;


import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.breadtrip.sdk.http.BaseRequestParams;
import com.breadtrip.sdk.http.IParser;

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

    public RequestParams(String url) {
        super(url);
        init();
    }

    public RequestParams(String url, int method) {
        super(url, method);
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

        public Builder(String url) {
            this(url, Request.Method.GET);
        }

        public Builder(String url, int method) {
            params = new RequestParams(url, method);
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

        /**
         * 自动解析
         * @param parseClass 解析class
         * @return
         */
        public Builder setParseTag(Class parseClass) {
            params.setParseTag(parseClass);
            return this;
        }

        /**
         * 自动解析
         * @param parseType 解析type
         * @return
         */
        public Builder setParseTag(TypeReference parseType) {
            params.setParseTag(parseType);
            return this;
        }

        /**
         * 手动解析
         * @param parseManual 手动解析实现类
         * @return
         */
        public Builder setParseTag(IParser parseManual) {
            params.setParseTag(parseManual);
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
