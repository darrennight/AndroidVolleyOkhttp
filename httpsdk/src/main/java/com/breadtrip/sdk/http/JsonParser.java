package com.breadtrip.sdk.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.breadtrio.sdk.common.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 数据解析
 * <p/>
 * 对于服务器新规范<i> </br>{ </br> "status": 0, </br> "message": "", </br>"data": { </br> ... // 结果数据 </br> }, }
 * </i></br>解析为新结构
 * <p/>
 * 对于就数据结构，默认status为成功code，message位空，data位原始json数据
 *
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Title: JsonParser.java
 * @Description:
 * @date 2015年6月9日 下午2:39:16
 */
public class JsonParser {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_DATA = "data";

    public static JsonBaseModel parseJson(Object parseTag, String json) {
        JsonBaseModel result = null;
        if (parseTag == null) {
            return null;
        } else {
            try {
                result = dispatch(parseTag, json);
            } catch (JSONException e) {
                return null;
            }
        }
        return result;
    }

    private static JsonBaseModel dispatch(Object parser, String jsonString) throws JSONException {
         JsonBaseModel parseResult = new JsonBaseModel();
        JSONObject jsonObj = new JSONObject(jsonString);
        // 默认没有code参数的情况下，是正确的.(兼容旧数据结构)
        parseResult.setStatus(jsonObj.optString(KEY_STATUS, HttpConfig.SUCCESS_CODE));

        parseResult.setMessage(jsonObj.optString(KEY_MESSAGE));

        if (HttpConfig.SUCCESS_CODE.equals(parseResult.getStatus())) {
            // 返回值正确，解析data
            JSONObject dataObj = jsonObj.optJSONObject(KEY_DATA);
            if (dataObj != null && dataObj.length() != 0) {
                // 新数据结构，解析data数据
                parseResult.setData(doParse(parser, dataObj, parseResult.getStatus()));
            } else {
                // 旧数据结构，解析原始数据
                parseResult.setData(doParse(parser, jsonObj, parseResult.getStatus()));
            }
        }
        return parseResult;
    }

    private static Object doParse(Object parseTag, JSONObject jsonObj, String code) throws JSONException {
        if (parseTag instanceof IParser) {
            IParser parser = (IParser) parseTag;
            // 手动解析
            return parser.doParse(jsonObj);
        } else if (parseTag instanceof Class) {
            // 自动解析类
            Class c = (Class) parseTag;
            return JSON.parseObject(jsonObj.toString(), c);
        } else if (parseTag instanceof TypeReference) {
            // 自动解析type
            TypeReference type = (TypeReference) parseTag;
            return JSON.parseObject(jsonObj.toString(), type);
        } else {
            if (LogUtils.isDebugable()) {
                // 调试状态，抛出异常, 解析TAG类型错误
                throw new RuntimeException("解析TAG类型错误");
            }
            return null;
        }
    }

}
