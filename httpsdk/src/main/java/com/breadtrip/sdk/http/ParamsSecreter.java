package com.breadtrip.sdk.http;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.breadtrio.sdk.common.utils.EncodeUtils;
import com.breadtrio.sdk.common.utils.LogUtils;
import com.breadtrio.sdk.common.utils.PackageUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.http.net.Secret
 * @Description: post参数加密类
 * @date 15/8/10 下午7:19
 */
public class ParamsSecreter {

    /**
     * 获取加密参数
     * @param params post参数
     * @return 加密后的参数
     */
    public static Map<String, String> getSecretParams(Map<String, String> params) {
        if (params != null && !params.equals(Collections.emptyMap())) {
            TreeMap<String, String> sortMap = new TreeMap<>(comparator);
            sortMap.putAll(params);
            addSecretParams(sortMap);
            return sortMap;
        }
        return params;
    }

    /**
     * 加密，参数排序，
     *
     * @param sortMap 排序后的参数
     */
    private static void addSecretParams(@Nullable TreeMap<String, String> sortMap) {
        StringBuilder sb = new StringBuilder();

        if (sortMap == null) {
            return;
        }
        Iterator<Map.Entry<String, String>> iterator = sortMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String name = entry.getKey();
            String value = entry.getValue();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
                iterator.remove();
                continue;
            }
            value = EncodeUtils.urlEncode(value);
            sb.append(name).append("=").append(value);
            if (iterator.hasNext()) {
                sb.append("&");
            }

        }

        sb.append(PackageUtils.getVersionName(HttpManager.getInstance().getContext())).append(HttpConfig.KEY_SECRET);
        if (LogUtils.isDebugable()) {
            LogUtils.d("TAG", "拼接字符串 = " + sb.toString());
        }
        String sign = EncodeUtils.encodeMD5(sb.toString());
        sortMap.put(HttpConfig.KEY_SECRET, sign);

        if (LogUtils.isDebugable()) {
            LogUtils.d(HttpConfig.HTTP_TAG, "加密开始===========");
            for (Map.Entry<String, String> np : sortMap.entrySet()) {
                LogUtils.d(HttpConfig.HTTP_TAG, np.getKey() + " = " + np.getValue());
            }
            LogUtils.d(HttpConfig.HTTP_TAG, "加密结束===========");
        }
    }

    /**
     * Map key排序比较器
     */
    private static Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    };
}
