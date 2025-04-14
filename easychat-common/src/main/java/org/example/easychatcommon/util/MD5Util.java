package org.example.easychatcommon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    static MessageDigest MD5 = null;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /*
        字节->hex
     */
    public static String byteToHex(byte b) {
        return HEX_DIGITS[(b & 0xf0) >> 4] + "" + HEX_DIGITS[b & 0xf];
    }

    public static String bytesToHex(byte[] b, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < start + end; i++) {
            sb.append(byteToHex(b[i]));
        }
        return sb.toString();
    }

    public static String encodeHex(byte[] data) {
        return bytesToHex(data, 0, data.length);
    }

    //string->bytes->md5-bytes->16进制string
    public static String getStringMD5(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] data = str.getBytes(StandardCharsets.UTF_8);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //update传入 digest输出
            md5.update(data);
            //默认输出字节
            data = md5.digest();
            for (byte b : data) {
                sb.append(byteToHex(b));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getFileMD5(File file) throws IOException {
        try(FileInputStream fis = new FileInputStream(file)){
            int l;
            byte[] buffer = new byte[8192];
            while((l = fis.read(buffer)) != -1){
                MD5.update(buffer, 0, l);
            }
            return new String(encodeHex(MD5.digest()));
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public static String getFileMD5(byte[] data) throws IOException {
        MD5.update(data);
        return new String(encodeHex(MD5.digest()));
    }

}
