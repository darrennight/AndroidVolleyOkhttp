package com.breadtrip.sdk.image;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;


/**
 * Basic LRU Memory cache as L1 Image cache
 * 
 */
public class ImageLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

	private final String TAG = this.getClass().getSimpleName();

	public ImageLruCache(int maxSize) {
		super(maxSize);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public Bitmap getBitmap(String url) {
		VolleyLog.v(TAG, "Retrieved item from Mem Cache");
		if (TextUtils.isEmpty(url)) {
			return null;
		}

		Bitmap bitmap = null;
		// 从内存取图片
		bitmap = get(url);
		if (bitmap != null) {
			return bitmap;
		}

		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		VolleyLog.v(TAG, "Added item to Mem Cache");
		put(url, bitmap);
	}

	public void clearCache() {
		VolleyLog.v(TAG, "Clear mem cache");
		evictAll();
	}
}
