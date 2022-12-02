package com.kook.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.plugin.BasePlugin;

import java.io.IOException;

/**
 * @author hy
 * @date 2022/12/2 16:42
 */


public class TextCommand extends BasePlugin {

    /**
     * 发送常规文字代码
     * @param command
     */
    public void sendText(String command,String content){
        new JKookCommand(command)
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                MultipleCardComponent card = new CardBuilder()
                                        .setTheme(Theme.NONE)
                                        .setSize(Size.LG)
                                        .addModule(
                                                new HeaderModule(new PlainTextElement(
                                                        content, false))
                                        )
                                        .build();
                                reply(sender, message, card);
                            } else {
                                getLogger().info("This command is not available for console.");
                                // 这个 else 块是可选的，但为了用户体验，最好还是提醒一下
                            }
                        }
                )
                .register();
    }


    public String chiShenMe(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("X-APISpace-Token","f2d1h2f6hhoo5ll3eop5e1vc69ekozuh")
                .addHeader("Authorization-Type","apikey")
                .url("https://eolink.o.apispace.com/eat222/api/v1/forward/chishenme?size=1").get().build();
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
        assert jsonObject != null;
        if (jsonObject.get("code")!="200"){
            return "异常了";
        }
        return jsonObject.getJSONArray("data").get(0).toString();
    }

    private void reply(User sender, Message message, BaseComponent component) {
        if (message instanceof TextChannelMessage) {
            ((TextChannelMessage) message).getChannel().sendComponent(
                    component,
                    null,
                    null
            );
        } else {
            sender.sendPrivateMessage(component);
        }
    }
}
