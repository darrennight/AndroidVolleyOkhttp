package com.breadtrip.config;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.config
 * @Description: 缓存路径
 * @date 15/8/7 下午3:30
 */
public class PathConfig {
    /**
     * 根目录
     */
    public static final String ROOT_PATH = "/Breadtrip";
    /**
     * 图片缓存目录
     */
    public static final String IMAGE_CACHE = ROOT_PATH + "/ImageCache";
    /**
     * http缓存目录
     */
    public static final String HTTP_CACHE = ROOT_PATH + "/HTTP";
    /**
     * 下载缓存目录
     */
    public static final String IMAGE_DOWNLOAD = ROOT_PATH + "/Download";
    /**
     * 错误日志目录
     */
    public static final String ERROR_LOG = ROOT_PATH + "/Trace";
}
