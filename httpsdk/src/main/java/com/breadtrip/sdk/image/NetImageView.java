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
 * @Description: 网络图片imageview
 * @date 15/8/17 下午6:32
 */
public class NetImageView extends NetworkImageView{

    protected ImageLoader mImageLoader;

    public NetImageView(Context context) {
        super(context);
    }

    public NetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 加载图片
     *
     * @param url url
     */
    public void setImageUrl(String url) {
        LogUtils.d("ImageTask", "Image请求 url=" + url);
        setImageUrl(url, checkAndCreateImageLoader());
    }

    protected ImageLoader checkAndCreateImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = ImageVolleyManager.getInstance().getNetImageLoader();
        }
        return mImageLoader;
    }

    /**
     * 加载图片
     *
     * @param url url
     * @param defaultImage 默认图
     */
    public void setImageUrl(String url, @DrawableRes int defaultImage) {
        setDefaultImageResId(defaultImage);
        setImageUrl(url);
    }

    /**
     * 加载图片
     *
     * @param url url
     * @param defaultImage 默认图
     * @param errorImage 错误图
     */
    public void setImageUrl(String url, @DrawableRes int defaultImage, @DrawableRes int errorImage) {
        setErrorImageResId(errorImage);
        setImageUrl(url, defaultImage);
    }
}
