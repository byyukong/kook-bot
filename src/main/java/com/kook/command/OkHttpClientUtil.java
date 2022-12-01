package com.kook.command;


import com.alibaba.fastjson.JSON;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OkHttpClientUtil {

    public static Map<String,Object> get(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call=client.newCall(request);
        Map<String,Object> map = new HashMap();
        try {
            Response response =call.execute();
            map = JSON.parseObject(response.body().string());
            response.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static String getString(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call=client.newCall(request);
        String res = "";
        try {
            Response response =call.execute();
            res = response.body().string();
            response.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

}
