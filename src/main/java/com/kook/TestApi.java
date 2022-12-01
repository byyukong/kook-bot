package com.kook;


import com.alibaba.fastjson.JSON;
import com.kook.command.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TestApi {

    @Test
    public void testTranslationApi() {
        Map<String,Object> res = new HashMap<>();
        try {
            res = OkHttpClientUtil.get("https://api.vvhan.com/api/fy?text=苹果");
        }catch (Exception e){
            log.error("请求错误！");
        }


        if (null != res) {
            Map<String,Object> data = JSON.parseObject(res.get("data").toString(), Map.class);
            System.err.println(data.get("fanyi").toString());
        } else {
            log.error("请求错误！");
        }
    }

}
