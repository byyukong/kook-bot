package com.kook.Instruction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kook.pojo.weather.ResultsVo;
import com.kook.util.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;

import java.util.Map;

import static com.kook.util.SenderUtil.reply;

/**
 * 查询天气
 */
@Slf4j
public class WeatherInstruction {
    public static void getWeather () {
        new JKookCommand("weather")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                if (args.length == 1){
                                    Map<String,Object> map = OkHttpClientUtil.get(
                                            "https://api.seniverse.com/v3/weather/now.json" +
                                                    "?key=SCYrvkytJze9qyzOh&location=" + args[0] + "&language=zh-Hans&unit=c"
                                    );

                                    if (map.containsKey("status_code")){
                                        reply(sender, message, "查询不到城市！");
                                    }else {
                                        JSONArray results1 = JSON.parseArray(map.get("results").toString());

                                        ResultsVo resultsVo = JSON.parseObject(results1.get(0).toString(), ResultsVo.class);

                                        MultipleCardComponent card = new CardBuilder()
                                                .setTheme(Theme.NONE)
                                                .setSize(Size.LG)
                                                .addModule(
                                                        new ContextModule.Builder()
                                                                .add(new PlainTextElement("城市：" + resultsVo
                                                                        .getLocation().getName() + "\n" + "天气：" +
                                                                        resultsVo.getNow()
                                                                                .getText() + "\n" + "温度：" + resultsVo.getNow()
                                                                        .getTemperature(), false)).build()
                                                )
                                                .build();

                                        reply(sender, message, card);

                                    }


                                }else {
                                    reply(sender, message, "请输入需要查询的城市，/weather 城市");
                                }

                            } else {
                                log.info("This command is not available for console.");
                            }
                        }
                )
                .register();
    }
}
