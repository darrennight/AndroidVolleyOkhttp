package com.breadtrio.sdk.common.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;

/**
 * 存储工具类
 */
public class StorageUtils {

	private static String sdCardPath = null;

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return 是否有sd卡
	 */
	public static boolean isSDCardExists() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

	/**
	 * 获取SD卡路径
	 * 
	 * @return sd路径
	 */
	public static String getSdCardPath() {
		if (TextUtils.isEmpty(sdCardPath)) {
			sdCardPath = Environment.getExternalStorageDirectory().getPath();
			if (!sdCardPath.endsWith(File.separator)) {
				sdCardPath += File.separator;
			}
		}
		return sdCardPath;
	}

	/**
	 * 获取可用空间
	 * 
	 * @return 可用空间
	 */
	@SuppressWarnings("deprecation")
    public static long getAvailableSpace() {
		long localSpace = -1L;
		if (isSDCardExists()) {
			File file = new File(getSdCardPath());
            boolean mkdirs = file.mkdirs();
            if(!mkdirs){
                LogUtils.d("TAG", "file mkdirs false");
            }
            StatFs localStatFs = new StatFs(getSdCardPath());
			localSpace = (long) localStatFs.getAvailableBlocks() * localStatFs.getBlockSize();
		}

		return localSpace;
	}

	/**
	 * 获取已用空间
	 * 
	 * @return 已用空间
	 */
	@SuppressWarnings("deprecation")
    public static long getUsedSpace() {
		long localSpace = -1L;
		if (isSDCardExists()) {
			File file = new File(getSdCardPath());
            boolean mkdirs = file.mkdirs();
            if(!mkdirs){
                LogUtils.d("TAG", "file mkdirs false");
            }
			StatFs localStatFs = new StatFs(getSdCardPath());
			localSpace = (long) localStatFs.getBlockCount() * localStatFs.getBlockSize();
		}

		return (localSpace - getAvailableSpace());
	}
}
