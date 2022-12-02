package com.kook.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Suww
 * @createTime 2022-12-02
 */
public class PictureUtils {
    public static File UrlToFile(String url) throws Exception {
            HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
            httpUrl.connect();
            InputStream ins=httpUrl.getInputStream();
            File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "xia");
            if (file.exists()) {
                file.delete();//如果缓存中存在该文件就删除
            }
            return file;
    }
}