package com.kook;

// import 被忽略

import com.alibaba.fastjson.JSON;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.plugin.BasePlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends BasePlugin {
    @Override
    public void onLoad() {
        // 重写 onLoad 并不是必须的，但是您可以在此阶段做一些初始化工作，比如解压默认的配置文件 (saveDefaultConfig)1
        // 三个方法内的日志记录也不是必须的，但是善用日志记录有助于描述 Bot 的运行状态！
        getLogger().info("PingBot 加载完成！"); // getLogger() 返回的是一个 org.slf4j.Logger 的实例对象，为日志记录器
    }

    @Override
    public void onEnable() {
        new JKookCommand("hito")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) { // 确保是个 Kook 用户在执行此命令
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder().url("https://v1.hitokoto.cn/").get().build();
                                Call call=client.newCall(request);
                                Map<String,Object> map = new HashMap();
                                try {
                                    Response response =call.execute();
                                    map = JSON.parseObject(response.body().string());
                                    response.close();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                reply(sender, message, map.get("hitokoto").toString());
                            } else {
                                getLogger().info("This command is not available for console.");
                                // 这个 else 块是可选的，但为了用户体验，最好还是提醒一下
                                // 另外，我们假设此执行器是在 Bot#onEnable 里写的，所以我们可以使用 getLogger() 。
                            }
                        }
                )
                .register();


        new JKookCommand("赵腾鹏")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) { // 确保是个 Kook 用户在执行此命令
                                reply(sender, message, "笨逼！");
                            } else {
                                getLogger().info("This command is not available for console.");
                                // 这个 else 块是可选的，但为了用户体验，最好还是提醒一下
                                // 另外，我们假设此执行器是在 Bot#onEnable 里写的，所以我们可以使用 getLogger() 。
                            }
                        }
                )
                .register();


        getLogger().info("PingBot 启动成功！");
    }


    private void reply(User sender, Message message, String content) {
        /*if (message instanceof TextChannelMessage) {
            ((TextChannelMessage) message).getChannel().sendComponent(
                    new MarkdownComponent(content),
                    (TextChannelMessage) message,null
            );
        } else {
            sender.sendPrivateMessage(new MarkdownComponent(content));
        }*/

        ((TextChannelMessage) message).getChannel().sendComponent(
                new MarkdownComponent(content),
                (TextChannelMessage) message,null//传入参数表示仅本人可见
        );
    }

    private void reply(User sender, Message message, BaseComponent component) {
        if (message instanceof TextChannelMessage) {
            ((TextChannelMessage) message).getChannel().sendComponent(
                    component,
                    null, //(TextChannelMessage) message,
                    sender
            );
        } else {
            sender.sendPrivateMessage(component);
        }
    }



    @Override
    public void onDisable() {
        getLogger().info("PingBot 已停止！");
    }
}
