package com.breadtrip.sdk.http;

import org.json.JSONObject;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.http.Parser
 * @Description: 解析接口
 * @date 15/8/10 上午11:55
 */
public interface IParser<T> {

    /**
     * 手动解析
     * @param jsonObj JSONObject
     * @return 解析类
     */
    public T doParse(JSONObject jsonObj);
}
