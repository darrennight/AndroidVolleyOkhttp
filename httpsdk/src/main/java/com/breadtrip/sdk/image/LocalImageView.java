package com.breadtrip.sdk.image;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.breadtrio.sdk.common.utils.LogUtils;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.image
 * @Description: 本地图片请求imageview
 * @date 15/8/17 下午8:47
 */
public class LocalImageView extends NetworkImageView {

    protected ImageLoader mImageLoader;

    public LocalImageView(Context context) {
        super(context);
    }

    public LocalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 加载图片
     * @param path 本地图片路径
     */
    public void setImagePath(String path) {
        LogUtils.d("ImageTask", "Image请求 url=" + path);
        setImageUrl(path, checkAndCreateImageLoader());
    }

    protected ImageLoader checkAndCreateImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = ImageVolleyManager.getInstance().getLocalImageLoader();
        }
        return mImageLoader;
    }

    /**
     * 加载图片
     * @param path 本地图片路径
     * @param defaultImage 默认图
     */
    public void setImagePath(String path, @DrawableRes int defaultImage) {
        setDefaultImageResId(defaultImage);
        setImagePath(path);
    }

    /**
     * 加载图片
     * @param path 本地图片路径
     * @param defaultImage 默认图
     * @param errorImage error图
     */
    public void setImagePath(String path, @DrawableRes int defaultImage, @DrawableRes int errorImage) {
        setErrorImageResId(errorImage);
        setImagePath(path, defaultImage);
    }
}
