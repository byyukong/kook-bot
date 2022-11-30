package com.kook.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pojo.music.BizContent;
import com.pojo.music.CommonParameters;
import com.pojo.music.vo.WyyMusicVo;
import com.pojo.weather.ResultsVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.MarkdownComponent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hy
 * @date 2022/11/30 13:51
 */

@Slf4j
public class MusicCommand {

    /**
     * appId
     */

    private static final String WYYAPPID="a301020000000000ac22dc8fa20e3795";

    /**
     *  token
     */
    private static final String WYYACCESSTOKEN="a301020000000000ac22dc8fa20e3795";

    private static final String WYYHTTPHEAD="http://openapi.music.163.com/";




    public void musicSearch(){
        new JKookCommand("/musicSearch")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                            }
                        }
                )
                .register();
    }



    /**
     * 网易云音乐搜索
     * @return
     */
    public JSONObject musicBasicSearch(String keyword){
        BizContent con=new BizContent();
        con.setKeyword(keyword);
        con.setLimit(5);
        con.setOffset(2);
        CommonParameters bizContent=new CommonParameters();
        bizContent.setAccessToken(WYYACCESSTOKEN);
        bizContent.setAppId(WYYAPPID);
        bizContent.setBizContent(JSONObject.toJSONString(con));
        String url="openapi/music/basic/search/song/get/v2";
        RequestBody body = new FormBody.Builder().add("bizContent",bizContent.toString()).build();
        final JSONObject jsonObject = requestPostTools(WYYHTTPHEAD + url,body);
        return jsonObject;
    }



    //get
    public JSONObject requestGetTools(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call=client.newCall(request);
        JSONObject jsonObject = null;
        try {
            Response response =call.execute();
            jsonObject = JSON.parseObject(response.body().string());
            response.close();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //post
    public JSONObject requestPostTools(String url, RequestBody body){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(body).build();
        Call call=client.newCall(request);
        JSONObject jsonObject = null;
        try {
            Response response =call.execute();
            jsonObject = JSON.parseObject(response.body().string());
            response.close();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void reply(Message message, String content) {
        ((TextChannelMessage) message).getChannel().sendComponent(
                new MarkdownComponent(content),
                (TextChannelMessage) message,null
        );
    }


}
