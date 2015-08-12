package com.breadtrio.sdk.common.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 * PackageUtils
 * <ul>
 * <strong>Install package</strong>
 * <li>{@link PackageUtils#installNormal(Context, String)}</li>
 * </ul>
 * <ul>
 * <strong>Uninstall package</strong>
 * <li>{@link PackageUtils#uninstallNormal(Context, String)}</li>
 * </ul>
 * <ul>
 * <strong>Is system application</strong>
 * <li>{@link PackageUtils#isSystemApplication(Context)}</li>
 * <li>{@link PackageUtils#isSystemApplication(Context, String)}</li>
 * <li>{@link PackageUtils#isSystemApplication(PackageManager, String)}</li>
 * </ul>
 * <ul>
 * <strong>Others</strong>
 * <li>{@link PackageUtils#isTopActivity(Context, String)} whether the app whost
 * package's name is packageName is on the top of the stack</li>
 * <li>{@link #getVersionCode(Context)} 获取应用的VersionCode</li>
 * <li>{@link #getVersionName(Context)}获取应用的VersionName</li>
 * <li>{@link #getPackageName(Context)} 获取应用的PackageName</li>
 * <li>{@link #hasShortcut(Context)} 是否已经存在快捷方式</li>
 * </ul>
 * 
 * @author jiwei
 */
public class PackageUtils {
	public static final String TAG = PackageUtils.class.getSimpleName();

	private static int mAppVersionCode = -1;
	private static String mAppVersionName;
	private static String mAppPackageName;

	/**
	 * install package normal by system intent
	 * 
	 * @param context
	 * @param filePath
	 *            file path of package
	 * @return whether apk exist
	 */
	public static boolean installNormal(Context context, String filePath) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		File file = new File(filePath);
		if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
			return false;
		}

		i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * uninstall package normal by system intent
	 * 
	 * @param context
	 * @param packageName
	 *            package name of app
	 * @return whether package name is empty
	 */
	public static boolean uninstallNormal(Context context, String packageName) {
		if (packageName == null || packageName.length() == 0) {
			return false;
		}

		Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder(32).append("package:").append(packageName).toString()));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * whether context is system application
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isSystemApplication(Context context) {
		if (context == null) {
			return false;
		}

		return isSystemApplication(context, context.getPackageName());
	}

	/**
	 * whether packageName is system application
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isSystemApplication(Context context, String packageName) {
		if (context == null) {
			return false;
		}

		return isSystemApplication(context.getPackageManager(), packageName);
	}

	/**
	 * whether packageName is system application
	 * 
	 * @param packageManager
	 * @param packageName
	 * @return <ul>
	 *         <li>if packageManager is null, return false</li>
	 *         <li>if package name is null or is empty, return false</li>
	 *         <li>if package name not exit, return false</li>
	 *         <li>if package name exit, but not system app, return false</li>
	 *         <li>else return true</li>
	 *         </ul>
	 */
	public static boolean isSystemApplication(PackageManager packageManager, String packageName) {
		if (packageManager == null || packageName == null || packageName.length() == 0) {
			return false;
		}

		try {
			ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
			return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
		} catch (NameNotFoundException e) {
			LogUtils.w(TAG, e);
		}
		return false;
	}

	/**
	 * whether the app whost package's name is packageName is on the top of the
	 * stack
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>You should add <strong>android.permission.GET_TASKS</strong> in
	 * manifest</li>
	 * </ul>
	 * 
	 * @param context
	 * @param packageName
	 * @return if params error or task stack is null, return false, otherwise
	 *         retun whether the app is on the top of stack
	 */
	public static boolean isTopActivity(Context context, String packageName) {
		if (context == null || TextUtils.isEmpty(packageName)) {
			return false;
		}

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo == null || tasksInfo.size() == 0) {
			return false;
		}
		try {
			return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
		} catch (Exception e) {
			LogUtils.w(TAG, e);
			return false;
		}
	}

	/**
	 * 获取应用的VersionName
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		if (TextUtils.isEmpty(mAppVersionName)) {
			initVersionInfo(context);
		}
		return mAppVersionName;
	}

	/**
	 * 获取应用的VersionCode
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		if (mAppVersionCode == -1) {
			initVersionInfo(context);
		}
		return mAppVersionCode;
	}
	
	public static String getPackageName(Context context){
		if(TextUtils.isEmpty(mAppPackageName)){
            initVersionInfo(context);
        }
        return mAppPackageName;
	}

	/**
	 * 初始化版本信息值
	 * 
	 * @param context
	 */
	private static void initVersionInfo(Context context) {
		String packageName = context.getPackageName();
		try {
			PackageInfo pm = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
			mAppVersionCode = pm.versionCode;
			mAppVersionName = pm.versionName;
			mAppPackageName = pm.packageName;
		} catch (NameNotFoundException e) {
			LogUtils.w(TAG, e);
		}
	}

	public static boolean hasShortcut(Context context) {
		boolean result = false;
		// 获取当前应用名称
		String title = null;
		try {
			final PackageManager pm = context.getPackageManager();
			title = pm.getApplicationLabel(pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}

		final String uriStr;
		if (android.os.Build.VERSION.SDK_INT < 8) {
			uriStr = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		final Uri CONTENT_URI = Uri.parse(uriStr);
		final Cursor c = context.getContentResolver().query(CONTENT_URI, null, "title=?", new String[] { title }, null);
		if (c != null && c.getCount() > 0) {
			result = true;
		}
		return result;
	}
}
