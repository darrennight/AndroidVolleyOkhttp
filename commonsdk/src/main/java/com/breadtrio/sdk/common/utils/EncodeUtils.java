package com.breadtrio.sdk.common.utils;


import com.breadtrio.sdk.common.encryppt.Base64;
import com.breadtrio.sdk.common.encryppt.HashEncrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * EncodeUtils
 * <ul>
 * <li>{@link #encodeBase64(byte[])}Base64加密</li>
 * <li>{@link #encodeBase64(String)}Base64加密</li>
 * <li>{@link #decodeBase64(String)} Base64解密</li>
 * <li>{@link #encodeMD5(String)}MD5加密，共32位</li>
 * <li>{@link #encodeSHA1(String)}SHA1加密，共40位</li>
 * <li>{@link #encodeSHA256(String)}SHA256加密，共64位</li>
 * </ul>
 * 
 * @author jiwei
 * @since 2013-11-18
 */
public class EncodeUtils {

    private static final String UTF_8 = "UTF-8";

    private static char[] DigitLower = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static char[] DigitUpper = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * Base64加密
     * 
     * @param content
     * @return
     */
    public static String encodeBase64(String content) {
        return Base64.encode(content.getBytes());
    }

    /**
     * Base64加密
     * 
     * @param bytes
     * @return
     */
    public static String encodeBase64(byte[] bytes) {
        return Base64.encode(bytes);
    }

    /**
     * Base64解密
     * 
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] decodeBase64(String content) throws UnsupportedEncodingException {
        return Base64.decode(content);
    }

    /**
     * MD5加密，共32位
     * 
     * @param content
     * @return
     */
    public static String encodeMD5(String content) {
        return HashEncrypt.encode(HashEncrypt.CryptType.MD5, content);
    }

    /**
     * SHA1加密，共40位
     * 
     * @param content
     * @return
     */
    public static String encodeSHA1(String content) {
        return HashEncrypt.encode(HashEncrypt.CryptType.SHA1, content);
    }

    /**
     * SHA256加密，共64位
     * 
     * @param content
     * @return
     */
    public static String encodeSHA256(String content) {
        return HashEncrypt.encode(HashEncrypt.CryptType.SHA256, content);
    }

    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, UTF_8).replaceAll("\\+", "%20").replaceAll("%7E", "~")
                    .replaceAll("\\*", "%2A");
        } catch (UnsupportedEncodingException e) {
            LogUtils.e(e.toString());
        }
        return null;
    }

    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, UTF_8);
        } catch (UnsupportedEncodingException e) {
            LogUtils.e(e.toString());
        }
        return null;
    }

    /**
     * @param srcStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NullPointerException
     */
    public static String getMD5Lower(String srcStr) {
        String sign = "lower";
        try {
            return processStr(srcStr, sign);
        } catch (NoSuchAlgorithmException e) {
        } catch (NullPointerException e) {
        }
        return "";
    }

    /**
     * @param srcStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NullPointerException
     */
    public static String getMD5Upper(String srcStr) {
        String sign = "upper";
        try {
            return processStr(srcStr, sign);
        } catch (NoSuchAlgorithmException e) {
        } catch (NullPointerException e) {
        }
        return "";
    }

    private static String processStr(String srcStr, String sign) throws NoSuchAlgorithmException, NullPointerException {
        MessageDigest digest;
        String algorithm = "MD5";
        String result = "";
        digest = MessageDigest.getInstance(algorithm);
        digest.update(srcStr.getBytes());
        byte[] byteRes = digest.digest();

        int length = byteRes.length;

        for (int i = 0; i < length; i++) {
            result = result + byteHEX(byteRes[i], sign);
        }

        return result;
    }

    /**
     * @param bt
     * @return
     */
    private static String byteHEX(byte bt, String sign) {

        char[] temp = null;
        if (sign.equalsIgnoreCase("lower")) {
            temp = DigitLower;
        } else if (sign.equalsIgnoreCase("upper")) {
            temp = DigitUpper;
        } else {
            throw new RuntimeException("");
        }
        char[] ob = new char[2];

        ob[0] = temp[(bt >>> 4) & 0X0F];

        ob[1] = temp[bt & 0X0F];

        return new String(ob);
    }

}
