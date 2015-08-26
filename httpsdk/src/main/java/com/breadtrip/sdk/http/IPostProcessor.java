package com.breadtrip.sdk.http;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.http
 * @Description: 解析后处理器，json解析后处理器，根据需求将json解析结果后处理。
 * @date 15/8/24 下午3:49
 */
public interface IPostProcessor {
    /**自定义数据处理*/
    Object doPostProcess(Object origin);
}
