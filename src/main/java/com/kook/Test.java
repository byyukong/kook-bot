package com.kook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pojo.weather.ResultsVo;
import com.util.OkHttpClientUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        System.err.println(OkHttpClientUtil.getString("http://api.gt5.cc/api/dog"));
    }
}
