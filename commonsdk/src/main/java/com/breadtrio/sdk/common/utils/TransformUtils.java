package com.breadtrio.sdk.common.utils;

import java.text.DecimalFormat;
import java.util.Locale;

public class TransformUtils {
	private final static long OneMB = 1024L * 1024L;
	private final static long OneGB = 1024L * 1024L * 1024L;

	/**
	 * 改格式 把存储空间/文件大小转换成String型，1G以上用GB表示，1G以下用MB表示，保留两位小数，不足的补0 e.g.
	 * 1.25GB、998.60MB
	 * 
	 * @param size
	 * @return
	 */
	public static String getSizeString(long size) {
		if (size <= 0L) {
			size = 0L;
		}
		String result = "";
		DecimalFormat df = new DecimalFormat("0.00");
		float sizeInGB = -1.0f;
		float sizeInMB = -1.0f;
		// QLog.p("size = " + size);
		sizeInGB = (float) size / OneGB;
		if (sizeInGB < 1.0f) {
			sizeInMB = (float) size / OneMB;
			result = df.format(sizeInMB) + "MB";
		} else {
			result = df.format(sizeInGB) + "GB";
		}

		return result;
	}

	/**
	 * 获取视频duration的字符串格式
	 * 
	 * @param durationMs
	 * @return
	 */
	public static String getDurationString(int durationMs) {
		int seconds = durationMs / 1000;
		// 不足1秒时，按1秒算
		if (seconds == 0 && durationMs > 0) {
			seconds = 1;
		}
		int minute = seconds / 60;
		int hour = minute / 60;
		minute = minute % 60;
		int second = seconds % 60;
		String string;
		if (hour == 0) {
			string = String.format(Locale.US, "%02d:%02d", minute, second);
		} else {
			string = String.format(Locale.US, "%02d:%02d:%02d", hour, minute, second);
		}
		return string;
	}
}
