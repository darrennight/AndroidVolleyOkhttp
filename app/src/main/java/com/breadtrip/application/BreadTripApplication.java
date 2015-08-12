package com.breadtrip.application;

import android.app.Application;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;

import com.breadtrio.sdk.common.utils.DeviceUtils;
import com.breadtrio.sdk.common.utils.PackageUtils;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.application
 * @Description: application
 * @date 15/8/12 上午11:24
 */
public class BreadTripApplication extends Application{

    private Handler mHandler;

    private static BreadTripApplication mInstance;

    // 旧逻辑，涉及HTTP请求的useragent，修改地图时修改，
    public static String mapType = "Map/GoogleMap/v1";

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mHandler = new Handler();

        String processName = DeviceUtils.getProcessName(this, Process.myPid());
        if (!TextUtils.isEmpty(processName) && processName.equals(PackageUtils.getPackageName(this))) {
            // 面包主task
        } else {
            // 其他task，eg：remote service；地图进程；统计进程等。
        }
    }

    /**
     * 获取application实例
     * @return application
     */
    public static BreadTripApplication getInstance() {
        return mInstance;
    }
}
