package org.qhs.example.utils;

import org.thymeleaf.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FilterUtil {

    public static boolean authentication(String timestamp, String nonce, String uid, String uinfo, String ext, String signature) {
        String sign_data_sha256 = calcRequestSign();
        return sign_data_sha256.equalsIgnoreCase(signature);
    }

    public static String calcRequestSign() {
        long timestamp = System.currentTimeMillis();
        String uinfo = "";
        String ext = "";

        String sign_date = String.format("%s%s%s,%s,%s%s", timestamp,
                Math.random(), 123,
                StringUtils.isEmpty(uinfo) ? "" : uinfo,
                StringUtils.isEmpty(ext) ? "" : ext, timestamp);
        return toSHA256(sign_date).toUpperCase();
    }
    //SHA-256加密
    public static String toSHA256(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256"); messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    //byte转换成16进制
    public static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer(); String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            System.out.println(temp.length());
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
