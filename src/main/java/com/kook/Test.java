package com.kook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pojo.weather.ResultsVo;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.seniverse.com/v3/weather/now.json?key=SCYrvkytJze9qyzOh&location=厦门1&language=zh-Hans&unit=c").get().build();
        Call call = client.newCall(request);
        Map<String,Object> map = new HashMap<>();
        try {
            Response response = call.execute();
            map = JSON.parseObject(response.body().string());
            response.close();

        } catch (IOException e) {
        }
        if (map.containsKey("status_code")){
            System.err.println("查询不到城市！");
        }
        JSONArray results1 = JSON.parseArray(map.get("results").toString());

        ResultsVo resultsVo = JSON.parseObject(results1.get(0).toString(), ResultsVo.class);

        System.err.println("城市：" + resultsVo.getLocation().getName() + "\t" + "天气：" + resultsVo.getNow().getText() + "\t" + "温度：" + resultsVo.getNow().getTemperature());

    }
}
