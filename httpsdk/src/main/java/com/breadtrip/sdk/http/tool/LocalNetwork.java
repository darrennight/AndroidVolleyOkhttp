package com.breadtrip.sdk.http.tool;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.breadtrio.sdk.common.utils.InputStreamUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrip.sdk.http.tool
 * @Description: 本地请求
 * @date 15/8/17 下午5:30
 */
public class LocalNetwork implements Network {

    @Override
    public NetworkResponse performRequest(Request<?> request) throws VolleyError {
        String filePath = request.getOriginUrl();
        File file = new File(filePath);
        // if the entry does not exist
        if (file.exists()) {
            try {
                byte[] data = InputStreamUtils.readFileToByte(new FileInputStream(file));
                return new NetworkResponse(200, data, request.getHeaders(), false);
            } catch (Exception e) {

            }
        }
        throw new VolleyError("can't decode bitmap from file path :" + filePath);
    }
}
