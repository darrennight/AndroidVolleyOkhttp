package com.breadtrip.sdk.http;

import android.text.TextUtils;

import com.android.volley.Request;
import com.breadtrio.sdk.common.utils.EncodeUtils;
import com.breadtrio.sdk.common.utils.LogUtils;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求参数封装类
 *
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Title: BaseRequestParams.java
 * @Description: 基本参数类
 * @date 2015年6月8日 下午7:19:25
 */
public class BaseRequestParams {

    private static final String QUERY_SEPARATOR = "?";
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String NAME_VALUE_SEPARATOR = "=";

    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求方法
     */
    private int method;

    /**
     * url中的请求参数
     */
    private ConcurrentHashMap<String, String> queryParams;
    /**
     * header参数
     */
    private ConcurrentHashMap<String, String> headerParams;
    /**
     * body中的参数
     */
    private ConcurrentHashMap<String, String> entityStringParams;
    /**
     * 上传的参数
     */
    private ConcurrentHashMap<String, File> fileParams;

    /**
     * 是否加密
     */
    private boolean isSecret = false;

    /**
     * 是否需要重试
     */
    private boolean isNeedRetry = true;

    /**
     * 解析TAG
     */
    private Object parseTag;

    /**
     * TAG
     */
    private Object tag;

    public BaseRequestParams(String url, Object parseTag) {
        this.url = url;
        this.parseTag = parseTag;
        this.method = Request.Method.GET;
    }

    public BaseRequestParams(String url, Object parseTag, int method) {
        this.url = url;
        this.parseTag = parseTag;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getMethod() {
        return method;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getParseTag() {
        return parseTag;
    }

    public boolean isSecret() {
        return isSecret;
    }

    public void setSecret(boolean secret) {
        this.isSecret = secret;
    }

    public boolean isNeedRetry() {
        return isNeedRetry;
    }

    public void setNeedRetry(boolean isNeedRetry) {
        this.isNeedRetry = isNeedRetry;
    }

    public ConcurrentHashMap<String, String> getHeaderParams() {
        return headerParams;
    }

    public ConcurrentHashMap<String, String> getEntityStringParams() {
        return entityStringParams;
    }

    public ConcurrentHashMap<String, File> getFileEntityParams() {
        return fileParams;
    }

    public void addQueryParam(String key, long value) {
        String stringValue = String.valueOf(value);
        addQueryParam(key, stringValue);
    }

    public void addQueryParam(String key, float value) {
        String stringValue = String.valueOf(value);
        addQueryParam(key, stringValue);
    }

    public void addQueryParam(String key, double value) {
        String stringValue = String.valueOf(value);
        addQueryParam(key, stringValue);
    }

    public void addQueryParam(String key, int value) {
        String stringValue = String.valueOf(value);
        addQueryParam(key, stringValue);
    }

    public void addQueryParam(String key, boolean value) {
        String stringValue = String.valueOf(value);
        addQueryParam(key, stringValue);
    }

    public void addQueryParam(String key, String value) {
        if (queryParams == null) {
            queryParams = new ConcurrentHashMap<String, String>();
        }

        queryParams.put(key, value);
    }

    public void clearQueryParams() {
        if (queryParams == null) {
            return;
        }

        queryParams.clear();
        queryParams = null;
    }

    public void addHeaderParam(String key, long value) {
        String stringValue = String.valueOf(value);
        addHeaderParam(key, stringValue);
    }

    public void addHeaderParam(String key, float value) {
        String stringValue = String.valueOf(value);
        addHeaderParam(key, stringValue);
    }

    public void addHeaderParam(String key, double value) {
        String stringValue = String.valueOf(value);
        addHeaderParam(key, stringValue);
    }

    public void addHeaderParam(String key, int value) {
        String stringValue = String.valueOf(value);
        addHeaderParam(key, stringValue);
    }

    public void addHeaderParam(String key, boolean value) {
        String stringValue = String.valueOf(value);
        addHeaderParam(key, stringValue);
    }

    public void addHeaderParam(String key, String value) {
        if (headerParams == null) {
            headerParams = new ConcurrentHashMap<String, String>();
        }
        headerParams.put(key, value);
    }

    public void addHeaderAll(Map<String, String> values) {
        if (headerParams == null) {
            headerParams = new ConcurrentHashMap<String, String>();
        }
        headerParams.putAll(values);
    }

    public void clearHeaderParams() {
        if (headerParams == null) {
            return;
        }

        headerParams.clear();
        headerParams = null;
    }

    public void addEntityStringParam(String key, long value) {
        String stringValue = String.valueOf(value);
        addEntityStringParam(key, stringValue);
    }

    public void addEntityStringParam(String key, float value) {
        String stringValue = String.valueOf(value);
        addEntityStringParam(key, stringValue);
    }

    public void addEntityStringParam(String key, double value) {
        String stringValue = String.valueOf(value);
        addEntityStringParam(key, stringValue);
    }

    public void addEntityStringParam(String key, int value) {
        String stringValue = String.valueOf(value);
        addEntityStringParam(key, stringValue);
    }

    public void addEntityStringParam(String key, boolean value) {
        String stringValue = String.valueOf(value);
        addEntityStringParam(key, stringValue);
    }

    public void addEntityStringParam(String key, String value) {
        if (entityStringParams == null) {
            entityStringParams = new ConcurrentHashMap<String, String>();
        }
        entityStringParams.put(key, value);
    }

    public void addEntityAll(Map<String, String> values) {
        if (entityStringParams == null) {
            entityStringParams = new ConcurrentHashMap<String, String>();
        }
        entityStringParams.putAll(values);
    }

    public void clearEntityStringParam() {
        if (entityStringParams == null) {
            return;
        }

        entityStringParams.clear();
        entityStringParams = null;
    }

    public void addFileParams(String key, String filePath) {
        if (fileParams == null) {
            fileParams = new ConcurrentHashMap<String, File>();
        }
        File file = new File(filePath);
        if (file.exists()) {
            fileParams.put(key, file);
        } else {
            LogUtils.e("图片不存在");
        }
    }

    public void clearFileStringParams() {
        if (fileParams == null) {
            return;
        }
        fileParams.clear();
        fileParams = null;
    }

    private String getQueryParamsString() {
        if (queryParams == null || queryParams.isEmpty()) {
            return null;
        }
        final StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : queryParams.entrySet()) {
            final String name = entry.getKey();
            final String value = entry.getValue();
            if (result.length() > 0)
                result.append(PARAMETER_SEPARATOR);
            result.append(name);
            result.append(NAME_VALUE_SEPARATOR);
            result.append(value);
        }
        return result.toString();
    }

    public String getUrlWithQueryString() {
        final StringBuilder result = new StringBuilder(this.url);
        final String queryParamsString = getQueryParamsString();
        if (!TextUtils.isEmpty(queryParamsString)) {
            String paramString = EncodeUtils.urlEncode(queryParamsString);
            if (result.indexOf(QUERY_SEPARATOR) == -1) {
                result.append(QUERY_SEPARATOR).append(paramString);
            } else {
                if (result.toString().endsWith(PARAMETER_SEPARATOR)) {
                    result.append(paramString);
                } else {
                    result.append(PARAMETER_SEPARATOR).append(paramString);
                }
            }
        }
        return result.toString();
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("<url>:").append(url)
                .append(";<method>:").append(method)
                .append(";<secret>:").append(isSecret);

        if (queryParams != null && !queryParams.equals(Collections.emptyMap())) {
            result.append(";<queryParams>:");
            for (ConcurrentHashMap.Entry<String, String> entry : queryParams.entrySet()) {
                result.append(entry.getKey());
                result.append(NAME_VALUE_SEPARATOR);
                result.append(entry.getValue());
                result.append(";");
            }
        }

        if (headerParams != null && !headerParams.equals(Collections.emptyMap())) {
            result.append("<headerParams>:");
            for (ConcurrentHashMap.Entry<String, String> entry : headerParams.entrySet()) {
                result.append(entry.getKey());
                result.append(NAME_VALUE_SEPARATOR);
                result.append(entry.getValue());
                result.append(";");
            }
        }

        if (entityStringParams != null && !entityStringParams.equals(Collections.emptyMap())) {
            result.append("<entityStringParams>:");
            for (ConcurrentHashMap.Entry<String, String> entry : entityStringParams.entrySet()) {
                result.append(entry.getKey());
                result.append(NAME_VALUE_SEPARATOR);
                result.append(entry.getValue());
                result.append(";");
            }
        }

        if (fileParams != null && !fileParams.equals(Collections.emptyMap())) {
            result.append("<fileStringParams>:");
            for (ConcurrentHashMap.Entry<String, File> entry : fileParams.entrySet()) {
                result.append(entry.getKey());
                result.append(NAME_VALUE_SEPARATOR);
                result.append(entry.getValue().getAbsolutePath());
                result.append(";");
            }
        }

        return result.toString();
    }

    protected String toShortString() {
        return "<url>:" + url;
    }
}
