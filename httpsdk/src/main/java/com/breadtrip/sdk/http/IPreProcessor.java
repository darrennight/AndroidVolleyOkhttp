package com.breadtrip.sdk.http;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.http
 * @Description: 解析前处理器，json字符串处理，将http请求回来的json字符串自定义处理
 * @date 15/8/24 下午3:52
 */
public interface IPreProcessor {
    /**自定义json字符串处理*/
    String doPreProcess(String origin);
}
