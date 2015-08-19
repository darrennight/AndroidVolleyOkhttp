package com.breadtrip.sdk.http.tool;

import android.content.Context;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.breadtrio.sdk.common.utils.PackageUtils;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.http.net
 * @Description: http请求队列
 * @date 15/8/5 下午5:52
 */
public class HttpQueue {

    public static final int DEFAULT_IMAGE_POOL_SIZE = 2;

    public static final int DEFAULT_NET_POOL_SIZE = 4;

    private HttpQueue() {
    }

    /**
     * 获取队列，默认缓存位置为包名地址
     *
     * @param context 上下文
     * @return volley 队列
     */
    public static RequestQueue newRequestQueue(Context context) {
        String cachePath = PackageUtils.getPackageName(context);
        return newRequestQueue(context, cachePath);
    }

    /**
     * 获取队列
     *
     * @param context   上下文
     * @param cachePath 缓存地址
     * @return volley 队列
     */
    public static RequestQueue newRequestQueue(Context context, String cachePath) {
        return newRequestQueue(context, cachePath, DEFAULT_NET_POOL_SIZE);
    }

    public static RequestQueue newRequestQueue(Context context, String cachePath, int poolSize) {
        // http请求，自定义cookie，持久化到shareprefrece中
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        CookieManager manager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);

        // okhttpstack,底层用ok
        HttpStack stack = new OkHttpStack();
        Network network = new BasicNetwork(stack);
        RequestQueue queue = new RequestQueue(new DiskBasedCache(new File(context.getCacheDir(), cachePath)), network, poolSize);
        queue.start();
        return queue;
    }

    /**
     * 获取本地请求队列
     *
     * @param context   上下文
     * @param cachePath 缓存路径
     * @return
     */
    public static RequestQueue newLocalRequestQueue(Context context, String cachePath) {
        Network network = new LocalNetwork();
        RequestQueue queue = new RequestQueue(new DiskBasedCache(new File(context.getCacheDir(), cachePath)), network, DEFAULT_IMAGE_POOL_SIZE);
        queue.start();
        return queue;
    }
}
