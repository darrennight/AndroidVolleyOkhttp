package com.breadtrio.sdk.common.encryppt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * DisplayUtils HASH加密工具(MD5,SHA1,SHA256)
 * <ul>
 * <li>{@link #encode(CryptType, String)}encode hash</li>
 * </ul>
 *
 * @author jiwei
 * @since 2013-11-18
 */
public class HashEncrypt {

    private static char[] DigitLower = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static char[] DigitUpper = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 加密类型
     */
    public enum CryptType {
        MD5, SHA1, SHA256
    }

    ;

    public static final String ALG_MD5 = "MD5";
    public static final String ALG_SHA1 = "SHA-1";
    public static final String ALG_SHA256 = "SHA-256";

    public static String encode(CryptType type, String content) {
        MessageDigest instance = null;
        byte[] encryptMsg = null;
        try {
            if (CryptType.MD5 == type) {
                instance = MessageDigest.getInstance(ALG_MD5);
            } else if (CryptType.SHA1 == type) {
                instance = MessageDigest.getInstance(ALG_SHA1);
            } else if (CryptType.SHA256 == type) {
                instance = MessageDigest.getInstance(ALG_SHA256);
            }
            if (instance == null) {
                throw new RuntimeException("instance is null");
            }
            encryptMsg = instance.digest(content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unbelievabl! How can u passby the method ? No such algorithm !");
        }
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < encryptMsg.length; i++) {
            switch (Integer.toHexString(encryptMsg[i]).length()) {
                case 1:
                    buffer.append("0");
                    buffer.append(Integer.toHexString(encryptMsg[i]));
                    break;
                case 2:
                    buffer.append(Integer.toHexString(encryptMsg[i]));
                    break;
                case 8:
                    buffer.append((Integer.toHexString(encryptMsg[i])).substring(6, 8));
                    break;

                default:
                    break;
            }
        }
        return buffer.toString();
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
