package com.jh.cavy.task.business.checkin;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class GZIPUtils {

    public static final String ENCODE_UTF_8 = "UTF-8";

    public static final String ENCODE_ISO_8859_1 = "ISO-8859-1";

    /**
     * String 压缩至gzip 字节数据
     */
    public static byte[] compress(String str) {
        return compress(str, ENCODE_UTF_8);
    }

    /**
     * String 压缩至gzip 字节数组，可选择encoding配置
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzipInputStream;
        try {
            gzipInputStream = new GZIPOutputStream(out);
            gzipInputStream.write(str.getBytes(encoding));
            gzipInputStream.close();
        } catch (IOException e) {
            System.out.println("gzip compress error");
        }
        return out.toByteArray();
    }

    /**
     * 字节数组解压
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gzipInputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            System.out.println("gzip uncompress error.");
        }

        return out.toByteArray();
    }

    /**
     * 字节数组解压至string
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, ENCODE_UTF_8);
    }

    /**
     * 字节数组解压至string，可选择encoding配置
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
            System.out.println("gzip uncompress to string error");
        }
        return null;
    }
    /**
     * 判断请求头是否存在gzip
     */
    public static boolean isGzip( HttpResponse response) {
        boolean gzip = false;
        for (Header key : response.getAllHeaders()) {
            if (key.getName().equalsIgnoreCase("Accept-Encoding") && Arrays.stream(response.getAllHeaders()).anyMatch(a->a.getName().equalsIgnoreCase("gzip"))
                        ||key.getName().equalsIgnoreCase("Content-Encoding") && Arrays.stream(response.getAllHeaders()).anyMatch(a->a.getName().equalsIgnoreCase("gzip"))) {
                gzip = true;
                break;
            }
        }
        return gzip;
    }

}
