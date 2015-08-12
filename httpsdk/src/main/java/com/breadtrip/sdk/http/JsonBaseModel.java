package com.breadtrip.sdk.http;

/**
 * 基本数据类型
 *
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Title: JsonBaseModel.java
 * @Description: 解析基础类
 * @date 2015年6月8日 下午2:45:28
 */
public class JsonBaseModel {
    /**
     * 返回值
     */
    private String status;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
