package com.breadtrip.sdk.image;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.breadtrip.sdk.http.tool.HttpQueue;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.image
 * @Description: Volley manager
 * 建议：整个项目使用volley取图，可以在application里面初始化。
 * 目前取图框架采用Fresco，volley取图只提供特殊用途，比如获取bitmap，
 * 在每个需哟使用的界面初始化onCreate，调用initiate方法，在onDestroy，调用stop方法。
 * 警告：谨慎使用initiate和stop，错误调用会引起占用线程资源
 * @date 15/8/17 下午5:25
 */
public class ImageVolleyManager {

    private static final int IMAGE_L1_CACHE_SIZE = 1024 * 1024 * 8;

    private static ImageVolleyManager instance;

    private RequestQueue mLocalRequestQueue;
    private RequestQueue mNetRequestQueue;

    private ImageLoader mLocalImageLoader;
    private ImageLoader mNetImageLoader;

    private ImageLruCache mImageCache;

    private int mReferenceCount;

    private ImageVolleyManager() {
    }

    /**
     * 获取单例
     *
     * @return 单例
     */
    public static ImageVolleyManager getInstance() {
        if (instance == null) {
            synchronized (ImageVolleyManager.class) {
                if (instance == null) {
                    instance = new ImageVolleyManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param applicationContext 上下文
     * @param cachePath          缓存地址
     */
    public synchronized void initiate(Context applicationContext, String cachePath) {
        if (mImageCache == null) {
            mImageCache = new ImageLruCache(IMAGE_L1_CACHE_SIZE);
        }

        if (mLocalImageLoader == null) {
            mLocalRequestQueue = HttpQueue.newLocalRequestQueue(applicationContext, cachePath);
            mLocalImageLoader = new ImageLoader(mLocalRequestQueue, mImageCache);
        }

        if (mNetImageLoader == null) {
            mNetRequestQueue = HttpQueue.newRequestQueue(applicationContext, cachePath, HttpQueue.DEFAULT_IMAGE_POOL_SIZE);
            mNetImageLoader = new ImageLoader(mNetRequestQueue, mImageCache);
        }

        // 增加一个引用数
        mReferenceCount++;
    }

    /**
     * 停止请求队列
     */
    public synchronized void stop() {

        // 减少一个引用数
        mReferenceCount--;

        // 当没有引用时，停止线程池，释放资源
        if (mReferenceCount == 0) {
            if (mLocalRequestQueue != null) {
                mLocalRequestQueue.stop();
                mLocalRequestQueue = null;
            }

            if (mNetRequestQueue != null) {
                mNetRequestQueue.stop();
                mNetRequestQueue = null;
            }

            mLocalImageLoader = null;
            mNetImageLoader = null;
        }
    }

    protected ImageLoader getLocalImageLoader() {
        return mLocalImageLoader;
    }

    protected ImageLoader getNetImageLoader() {
        return mNetImageLoader;
    }

    /**
     * 获取网络图片
     *
     * @param url      请求url
     * @param width    期望宽度，可为0
     * @param height   期望高度，可为0
     * @param listener 回调监听
     */
    public void startImageRequest(String url, int width, int height, final ImageResponseListener listener) {
        if (listener == null) {
            return;
        }

        if (!TextUtils.isEmpty(url)) {
            ImageLoader.ImageContainer imageContainer = mNetImageLoader.get(url, new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onFailure();
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (response.getBitmap() != null) {
                        listener.onSuccess(response.getBitmap());
                    }
                }
            }, width, height);
            if (imageContainer.getBitmap() != null) {
                listener.onSuccess(imageContainer.getBitmap());
            }
        }
    }

    /**
     * 获取本地请求图片
     *
     * @param path     图片地址
     * @param width    期望宽度，可为0
     * @param height   期望高度，可为0
     * @param listener 回调监听
     */
    public void startLocalImageRequest(String path, int width, int height, final ImageResponseListener listener) {
        if (listener == null) {
            return;
        }

        if (!TextUtils.isEmpty(path)) {
            ImageLoader.ImageContainer imageContainer = mLocalImageLoader.get(path, new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onFailure();
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (response.getBitmap() != null) {
                        listener.onSuccess(response.getBitmap());
                    }
                }
            }, width, height);
            if (imageContainer.getBitmap() != null) {
                listener.onSuccess(imageContainer.getBitmap());
            }
        }
    }
}
