package com.kook.Instruction;

import com.alibaba.fastjson.JSON;
import com.kook.mapper.SteamApiMapper;
import com.kook.pojo.Api;
import com.kook.pojo.chatgpt.ChatGptChoicesVo;
import com.kook.util.MybatisUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.ibatis.session.SqlSession;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.kook.util.SenderUtil.reply;

@Slf4j
public class ChatGptInstruction {

    private static SqlSession sqlSession = MybatisUtils.getSqlSession();
    private static SteamApiMapper steamApiMapper = sqlSession.getMapper(SteamApiMapper.class);


    public static void getGhat (){
        new JKookCommand("chat")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                if (args.length > 0) {
                                    Api api = steamApiMapper.getApiInfoById("c027cccc406f4b6b9a6160cadde8d584");
                                    String API_ENDPOINT = api.getApiUrl();
                                    String API_KEY = api.getAppKey();
                                    String prompt = "";

                                    for (String str : args) {
                                        prompt += str;
                                    }

                                    OkHttpClient client = new OkHttpClient.Builder()
                                            .connectTimeout(180, TimeUnit.SECONDS)
                                            .readTimeout(180, TimeUnit.SECONDS)
                                            .build();

                                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                                    String requestBody = "{\"prompt\":\"" + prompt + "\",\"max_tokens\":1024,\"temperature\":0.9,\"top_p\":1,\"frequency_penalty\":0.0,\"presence_penalty\":0.6}";

                                    Request request = new Request.Builder()
                                            .url(API_ENDPOINT)
                                            .header("Authorization", "Bearer " + API_KEY)
                                            .header("Content-Type", "application/json")
                                            .post(RequestBody.create(mediaType, requestBody))
                                            .build();



                                    Call call = client.newCall(request);
                                    Response response = null;
                                    String responseBody = null;
                                    try {
                                        response = call.execute();
                                        responseBody = response.body().string();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }


                                    Map<String, Object> map = JSON.parseObject(responseBody, Map.class);
                                    ChatGptChoicesVo choices = JSON.parseObject(map.get("choices").toString().substring(1, map.get("choices").toString().length() - 1), ChatGptChoicesVo.class);

                                    MultipleCardComponent card = new CardBuilder()
                                            .setTheme(Theme.NONE)
                                            .setSize(Size.LG)
                                            .addModule(
                                                    new ContextModule.Builder()
                                                            .add(new PlainTextElement(choices.getText(), false)).build()
                                            )
                                            .build();
                                    reply(sender, message, card);
                                }else {
                                    MultipleCardComponent card = new CardBuilder()
                                            .setTheme(Theme.NONE)
                                            .setSize(Size.LG)
                                            .addModule(
                                                    new ContextModule.Builder()
                                                            .add(new PlainTextElement("请输入问题！", false)).build()
                                            )
                                            .build();
                                    reply(sender, message, card);
                                }

                            } else {
                                log.info("This command is not available for console.");
                            }
                        }
                )
                .register();
    }


}
