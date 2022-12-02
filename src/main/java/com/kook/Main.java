package com.kook;

// import 被忽略

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kook.pojo.weather.ResultsVo;
import com.kook.util.OkHttpClientUtil;
import com.kook.util.PictureUtils;
import snw.jkook.JKook;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.entity.abilities.Accessory;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.ImageElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;
import snw.jkook.plugin.BasePlugin;

import java.io.File;
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
                                Map<String,Object> map = OkHttpClientUtil.get("https://v1.hitokoto.cn/");
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
                            if (sender instanceof User) {
                                MultipleCardComponent card = new CardBuilder()
                                        .setTheme(Theme.NONE)
                                        .setSize(Size.LG)
                                        .addModule(
                                                new HeaderModule(new PlainTextElement("笨逼！", false))
                                        )
                                        .build();
                                reply(sender, message, card);
                            } else {
                                getLogger().info("This command is not available for console.");
                                // 这个 else 块是可选的，但为了用户体验，最好还是提醒一下
                                // 另外，我们假设此执行器是在 Bot#onEnable 里写的，所以我们可以使用 getLogger() 。
                            }
                        }
                )
                .register();


        new JKookCommand("getId")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                MultipleCardComponent card = new CardBuilder()
                                        .setTheme(Theme.NONE)
                                        .setSize(Size.LG)
                                        .addModule(
                                                new HeaderModule(new PlainTextElement(
                                                        "用户名：" + sender.getName() + "\n" + "ID：" + sender.getId(), false))
                                        )
                                        .build();
                                reply(sender, message, card);
                            } else {
                                getLogger().info("This command is not available for console.");
                                // 这个 else 块是可选的，但为了用户体验，最好还是提醒一下
                                // 另外，我们假设此执行器是在 Bot#onEnable 里写的，所以我们可以使用 getLogger() 。
                            }
                        }
                )
                .register();


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
                                getLogger().info("This command is not available for console.");
                            }
                        }
                )
                .register();

        new JKookCommand("舔狗")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                String res = OkHttpClientUtil.getString("http://api.gt5.cc/api/dog");

                                if (null != res) {
                                    MultipleCardComponent card = new CardBuilder()
                                            .setTheme(Theme.NONE)
                                            .setSize(Size.LG)
                                            .addModule(
                                                    new ContextModule.Builder()
                                                    .add(new PlainTextElement(res, false)).build()
                                            )
                                            .build();
                                    reply(sender, message, card);
                                } else {
                                    reply(sender, message, "请求错误！");
                                }

                            } else {
                                getLogger().info("This command is not available for console.");
                            }
                        }
                )
                .register();


            new JKookCommand("社会")
                    .executesUser(
                            (sender, args, message) -> {
                                if (sender instanceof User) {
                                    String res = OkHttpClientUtil.getString("https://api.oick.cn/yulu/api.php");


                                    if (null != res) {
                                        MultipleCardComponent card = new CardBuilder()
                                                .setTheme(Theme.NONE)
                                                .setSize(Size.LG)
                                                .addModule(
                                                        new ContextModule.Builder()
                                                                .add(new PlainTextElement(res.substring(1,res.length() -1), false)).build()
                                                )
                                                .build();
                                        reply(sender, message, card);
                                    } else {
                                        reply(sender, message, "请求错误！");
                                    }

                                } else {
                                    getLogger().info("This command is not available for console.");
                                }
                            }
                    )
                    .register();



        new JKookCommand("/steambd")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                String res = OkHttpClientUtil.getString("https://api.oick.cn/yulu/api.php");


                                if (null != res) {
                                    MultipleCardComponent card = new CardBuilder()
                                            .setTheme(Theme.NONE)
                                            .setSize(Size.LG)
                                            .addModule(
                                                    new ContextModule.Builder()
                                                            .add(new PlainTextElement(res.substring(1,res.length() -1), false)).build()
                                            )
                                            .build();
                                    reply(sender, message, card);
                                } else {
                                    reply(sender, message, "请求错误！");
                                }

                            } else {
                                getLogger().info("This command is not available for console.");
                            }
                        }
                )
                .register();


        /*new JKookCommand("翻译")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                if (args.length == 1) {
                                    Map<String,Object> res = new HashMap<>();
                                    try {
                                        String url = "https://api.vvhan.com/api/fy?text=" + args[0];
                                        getLogger().info(url);
                                        res = OkHttpClientUtil.get(url);
                                    } catch (Exception e){

                                        reply(sender, message, "请求错误！");
                                    }
                                    if (null != res) {
                                        Map<String,Object> data = JSON.parseObject(res.get("data").toString(), Map.class);
                                        MultipleCardComponent card = new CardBuilder()
                                                .setTheme(Theme.NONE)
                                                .setSize(Size.LG)
                                                .addModule(
                                                        new ContextModule.Builder()
                                                                .add(new PlainTextElement("翻译结果：" + data.get("fanyi").toString(), false)).build()
                                                )
                                                .build();
                                        reply(sender, message, card);
                                    } else {
                                        reply(sender, message, "请求错误！");
                                    }
                                }else {
                                    reply(sender, message, "请输入需要翻译的内容，格式 /翻译 内容！");
                                }


                            } else {
                                getLogger().info("This command is not available for console.");
                            }
                        }
                )
                .register();*/

        new JKookCommand("摸鱼")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                Map<String,Object> res = OkHttpClientUtil.get("https://api.vvhan.com/api/moyu?type=json");
                                try {
                                    File file = PictureUtils.UrlToFile(res.get("url").toString());
                                    MultipleCardComponent card = new CardBuilder()
                                            .setTheme(Theme.NONE)
                                            .setSize(Size.LG)
                                            .addModule(new HeaderModule(new PlainTextElement("!!!", false)))
                                            .addModule(new SectionModule(new MarkdownElement("摸鱼图片:")
                                                    ,file != null ? new ImageElement(
                                                            JKook.getHttpAPI().uploadFile(file),
                                                    null,
                                                    Size.SM,
                                                    false
                                            ):null,
                                                    file !=null ? Accessory.Mode.RIGHT : null))
                                            .build();
                                    reply(sender, message, card);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                getLogger().info("This command is not available for console.");
                            }
                        }
                )
                .register();
        
        


        getLogger().info("PingBot 启动成功！");
    }


    private void reply(User sender, Message message, String content) {
        ((TextChannelMessage) message).getChannel().sendComponent(
                new MarkdownComponent(content),
                (TextChannelMessage) message,null
        );
    }

    private void reply(User sender, Message message, BaseComponent component) {
        if (message instanceof TextChannelMessage) {
            ((TextChannelMessage) message).getChannel().sendComponent(
                    component,
                    null, //(TextChannelMessage) message,
                    null
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
