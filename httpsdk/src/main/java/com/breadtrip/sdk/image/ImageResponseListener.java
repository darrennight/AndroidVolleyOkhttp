package com.breadtrip.sdk.image;

import android.graphics.Bitmap;

/**
 * 图片回调接口
 * @Title: ImageResponseListener.java  
 * @Description: 
 * @author jiwei@breadtrip.com
 * @date 2015年6月8日 下午2:41:42  
 * @version V1.0
 */
public interface ImageResponseListener {
	
	/**
	 * 下载成功
	 * @param bitmap 图片
	 */
	void onSuccess(Bitmap bitmap);

	/**
	 * 下载失败
	 */
	void onFailure();
}
