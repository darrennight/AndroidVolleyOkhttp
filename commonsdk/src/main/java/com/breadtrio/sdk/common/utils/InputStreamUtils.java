package com.breadtrio.sdk.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author jiwei@breadtrip.com
 * @version V1.0
 * @Project: BreadTrip
 * @Package com.breadtrio.sdk.common.utils
 * @Description: ${TODO}
 * @date 15/8/17 下午6:11
 */
public class InputStreamUtils {

    /**
     *
     * @param inStream 输入流
     * @return 数组
     * @throws Exception
     */
    public static byte[] readFileToByte(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }
}
