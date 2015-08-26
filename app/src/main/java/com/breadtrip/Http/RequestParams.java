package com.breadtrip.Http;


import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.breadtrip.sdk.http.BaseRequestParams;
import com.breadtrip.sdk.http.IParser;
import com.breadtrip.sdk.http.IPostProcessor;
import com.breadtrip.sdk.http.IPreProcessor;

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
     * @Description: builder构造RequestParams
     * @date 2015年6月8日 下午7:24:01
     */
    public static class Builder {

        private RequestParams params;

        /**
         * 构造方法
         * @param url 请求地址
         */
        public Builder(String url) {
            this(url, Request.Method.GET);
        }

        /**
         * 构造方法
         * @param url 请求地址
         * @param method 请求方法类型
         */
        public Builder(String url, int method) {
            params = new RequestParams(url, method);
        }

        /**
         * 设置请求方法
         * @param method 请求方法
         * @return 构造器
         */
        public Builder setMethod(int method) {
            params.setMethod(method);
            return this;
        }

        /**
         * 设置url参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addQueryParam(String key, long value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        /**
         * 设置url参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addQueryParam(String key, float value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        /**
         * 设置url参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addQueryParam(String key, double value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        /**
         * 设置url参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addQueryParam(String key, int value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        /**
         * 设置url参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addQueryParam(String key, boolean value) {
            String stringValue = String.valueOf(value);
            return addQueryParam(key, stringValue);
        }

        /**
         * 设置url参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addQueryParam(String key, String value) {
            params.addQueryParam(key, value);
            return this;
        }

        /**
         * 设置header参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addHeaderParam(String key, long value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        /**
         * 设置header拼接参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addHeaderParam(String key, float value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        /**
         * 设置header参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addHeaderParam(String key, double value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        /**
         * 设置header拼接参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addHeaderParam(String key, int value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        /**
         * 设置header参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addHeaderParam(String key, boolean value) {
            String stringValue = String.valueOf(value);
            return addHeaderParam(key, stringValue);
        }

        /**
         * 设置header参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addHeaderParam(String key, String value) {
            params.addHeaderParam(key, value);
            return this;
        }

        /**
         * 设置body参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addEntityStringParam(String key, long value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        /**
         * 设置body参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addEntityStringParam(String key, float value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        /**
         * 设置body参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addEntityStringParam(String key, double value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        /**
         * 设置body参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addEntityStringParam(String key, int value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        /**
         * 设置body参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addEntityStringParam(String key, boolean value) {
            String stringValue = String.valueOf(value);
            return addEntityStringParam(key, stringValue);
        }

        /**
         * 设置body参数
         * @param key 参数名称
         * @param value 参数值
         * @return 构造器
         */
        public Builder addEntityStringParam(String key, String value) {
            params.addEntityStringParam(key, value);
            return this;
        }

        /**
         * 设置fiel参数
         * @param key 参数名称
         * @param filePath 文件路径
         * @return 构造器
         */
        public Builder addFileParams(String key, String filePath) {
            params.addFileParams(key, filePath);
            return this;
        }

        /**
         * 设置是否参数加密
         * @param secret 是否加密
         * @return 构造器
         */
        public Builder setSecret(boolean secret) {
            params.setSecret(secret);
            return this;
        }

        /**
         * 设置是否需要失败重试
         * @param isNeedRetry 是否失败重试
         * @return 构造器
         */
        public Builder setNeedRetry(boolean isNeedRetry) {
            params.setNeedRetry(isNeedRetry);
            return this;
        }

        /**
         * 自动解析
         * @param parseClass 解析class
         * @return  构造器
         */
        public Builder setParseTag(Class parseClass) {
            params.setParseTag(parseClass);
            return this;
        }

        /**
         * 自动解析
         * @param parseType 解析type
         * @return 构造器
         */
        public Builder setParseTag(TypeReference parseType) {
            params.setParseTag(parseType);
            return this;
        }

        /**
         * 手动解析
         * @param parseManual 手动解析实现类
         * @return 构造器
         */
        public Builder setParseTag(IParser parseManual) {
            params.setParseTag(parseManual);
            return this;
        }

        /**
         * 设置请求tag，<b>注意和解析tag区分</b>
         * @param tag 请求tag
         * @return 构造器
         */
        public Builder setTag(Object tag) {
            params.setTag(tag);
            return this;
        }

        /**
         * 添加json解析预处理器，json解析前，进行json封装
         * @param preProcesser 预处理器
         * @return 构造器
         */
        public Builder setPreProcesser(IPreProcessor preProcesser) {
            params.setPreProcessor(preProcesser);
            return this;
        }

        /**
         * 设置json解析后处理器，json解析完成后，进行数据转换。
         * @param postProcesser 后处理器
         * @return 构造器
         */
        public Builder setPostProcesser(IPostProcessor postProcesser) {
            params.setPostProcessor(postProcesser);
            return this;
        }

        /**
         * 生成器RequestParams
         * @return RequestParams
         */
        public RequestParams create() {
            return params;
        }
    }
}
