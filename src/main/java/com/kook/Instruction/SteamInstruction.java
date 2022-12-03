package com.kook.Instruction;

import com.alibaba.fastjson.JSON;
import com.kook.mapper.SteamApiMapper;
import com.kook.pojo.Api;
import com.kook.pojo.SteamKook;
import com.kook.pojo.steam.SteamResponseVo;
import com.kook.util.MybatisUtils;
import com.kook.util.OkHttpClientUtil;
import com.kook.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import snw.jkook.JKook;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.entity.abilities.Accessory;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.ImageElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.module.SectionModule;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.kook.util.SenderUtil.reply;

@Slf4j
public class SteamInstruction {

    private static SqlSession sqlSession = MybatisUtils.getSqlSession();
    private static SteamApiMapper steamApiMapper = sqlSession.getMapper(SteamApiMapper.class);


    public static void bindSteam (){
        new JKookCommand("steambd")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                if (args.length == 1) {
                                    if (args[0].length() == 17) {
                                        if (steamApiMapper.getSteamBdCountByKookId(sender.getId()) > 0) {
                                            reply(sender, message, "已经绑定过了！");
                                        } else {
                                            steamApiMapper.addSteamBd(UUID.randomUUID().toString().replace("-",""),sender.getId(),args[0]);
                                            reply(sender, message, "绑定Steam成功！");
                                            sqlSession.commit();
                                        }
                                    }else {
                                        reply(sender, message, "SteamID格式不正确！");
                                    }

                                }else {
                                    reply(sender, message, "请输入SteamID，格式 /steambd 17位SeamID！");
                                }

                            } else {
                                log.info("This command is not available for console.");
                            }
                        }
                )
                .register();
    }


    public static void getStringInfo() {
        new JKookCommand("steaminfo")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                SteamKook steamInfo = steamApiMapper.getSteamBdInfoByKookId(sender.getId());
                                log.info("用户ID" + sender.getId());
                                log.info(JSON.toJSONString(steamInfo));
                                if (null != steamInfo){
                                    Api apiInfoById = steamApiMapper.getApiInfoById("9e04d6e3e8db4efa914e60fb94d80114");
                                    String url = apiInfoById.getApiUrl() + "?key=" + apiInfoById.getAppKey() + "&steamid=" + steamInfo.getSteamId() + "&count=3";
                                    Map<String, Object> res = OkHttpClientUtil.get(url);

                                    SteamResponseVo steamResponseVo = JSON.parseObject(res.get("response").toString(), SteamResponseVo.class);
                                    log.info("最近游玩游戏数：" + steamResponseVo.getTotalCount());


                                    steamResponseVo.getGames().forEach(item -> {
                                        Double playtime2weeks = Double.parseDouble(String.format("%.2f",Double.parseDouble(item.getPlaytime2weeks()) / 60));
                                        File file = null;
                                        try {
                                            file = PictureUtils.UrlToFile("https://steamcdn-a.akamaihd.net/steam/apps/" +item.getAppid()+ "/header.jpg");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        StringBuffer sb = new StringBuffer("游戏名：" + item.getName() + "\n");
                                        sb.append("最近两周游戏时间：" + playtime2weeks + "\n");
                                        sb.append("总时长：" + Double.parseDouble(String.format("%.2f",Double.parseDouble(item.getPlaytimeForever()) / 60)) + "\n");
                                        sb.append("Windows游戏时间：" + Double.parseDouble(String.format("%.2f",Double.parseDouble(item.getPlaytimeWindowsForever()) / 60)) + "\n");
                                        sb.append("MAC游戏时间：" + Double.parseDouble(String.format("%.2f",Double.parseDouble(item.getPlaytimeMacForever()) / 60)) + "\n");
                                        sb.append("Linux游戏时间：" + Double.parseDouble(String.format("%.2f",Double.parseDouble(item.getPlaytimeLinuxForever()) / 60)) + "\n");
                                        sb.append("游戏ID：" + item.getAppid() + "\n");


                                        MultipleCardComponent card = new CardBuilder()
                                                .setTheme(Theme.NONE)
                                                .setSize(Size.LG).addModule(new SectionModule(new MarkdownElement( sb.toString())
                                                        ,file != null ? new ImageElement(
                                                        JKook.getHttpAPI().uploadFile(file),
                                                        null,
                                                        Size.LG,
                                                        false
                                                ):null,
                                                        file !=null ? Accessory.Mode.RIGHT : null))
                                                .build();


                                        reply(sender, message, card);
                                    });





                                }else {
                                    reply(sender, message, "请先绑定Steam！");
                                }
                            } else {
                                log.info("This command is not available for console.");
                            }
                        }
                )
                .register();
    }


    /**
     * 获取个人信息
     */
    public static void steamPer (){
        new JKookCommand("steamper")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                SteamKook steamInfo = steamApiMapper.getSteamBdInfoByKookId("3568540449");
                                if (null != steamInfo){
                                    //personastate: 0隐身 | 离线，1在线，3离开
                                    Api apiInfoById = steamApiMapper.getApiInfoById("6e24e9f01c0a4beeac02fd1e154df5f9");
                                    String url = apiInfoById.getApiUrl() + "?key=" + apiInfoById.getAppKey() + "&steamids=" + steamInfo.getSteamId();
                                    Map<String, Object> res = OkHttpClientUtil.get(url);
                                    Map<String, Object> response = JSON.parseObject(res.get("response").toString(), Map.class);
                                    Map<String,Object> players = JSON.parseObject(JSON.toJSONString(response.get("players")).replace("[", "").replace("]", ""), Map.class);

                                    Api getLevelUrl = steamApiMapper.getApiInfoById("92141df427ea475796e8e276e7c856d6");
                                    String levelUrl = getLevelUrl.getApiUrl() + "?key=" + getLevelUrl.getAppKey() + "&steamid=" + steamInfo.getSteamId();
                                    Map<String, Object> levelRes = OkHttpClientUtil.get(levelUrl);
                                    Map<String, Object> levelResponse = JSON.parseObject(levelRes.get("response").toString(), Map.class);


                                    String statusCode = players.get("personastate").toString();
                                    String status = "";
                                    switch (statusCode) {
                                        case "0":
                                            status = "离线";
                                            break;
                                        case "1":
                                            status = "在线";
                                            break;
                                        case "3":
                                            status = "离开";
                                            break;
                                    }
                                    String timecreatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(players.get("timecreated").toString()) * 1000));
                                    String lastlogoffDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(players.get("lastlogoff").toString()) * 1000));

                                    StringBuffer sb = new StringBuffer("用户名：" + players.get("personaname").toString() + "\n");
                                    sb.append("等级：" + levelResponse.get("player_level") + "\n");
                                    sb.append("状态：" + status + "\n");
                                    if (players.containsKey("gameextrainfo")){
                                        sb.append("正在玩：" + players.get("gameextrainfo") + "\n");
                                    }
                                    sb.append("最后登录时间：" + lastlogoffDate + "\n");
                                    sb.append("账号创建时间：" + timecreatedDate);

                                    File file = null;
                                    try {
                                        file = PictureUtils.UrlToFile(players.get("avatarfull").toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    MultipleCardComponent card = new CardBuilder()
                                            .setTheme(Theme.NONE)
                                            .setSize(Size.LG).addModule(new SectionModule(new MarkdownElement(sb.toString())
                                                    ,file != null ? new ImageElement(
                                                    JKook.getHttpAPI().uploadFile(file),
                                                    null,
                                                    Size.LG,
                                                    false
                                            ):null,
                                                    file !=null ? Accessory.Mode.RIGHT : null))
                                            .build();


                                    reply(sender, message, card);


                                }else {
                                    reply(sender,message,"请先绑定Steam！");
                                }
                            } else {
                                log.info("This command is not available for console.");
                            }
                        }
                )
                .register();
    }


}
