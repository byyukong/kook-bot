package com.kook;


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
import org.junit.Test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.kook.util.SenderUtil.reply;

@Slf4j
public class TestApi {
    SqlSession sqlSession = MybatisUtils.getSqlSession();

    private SteamApiMapper steamApiMapper = sqlSession.getMapper(SteamApiMapper.class);

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

    @Test
    public void testApi() {
        Map<String,Object> res = new HashMap<>();
        try {
            res = OkHttpClientUtil.get("https://api.vvhan.com/api/moyu?type=json");
            File file = PictureUtils.UrlToFile(res.get("url").toString());
            System.out.println(file);
        }catch (Exception e){
            log.error("请求错误！");
        }


    }


    @Test
    public void steamBdTest(){
        try {
//            System.out.println(steamApiMapper.getApiInfoById("9e04d6e3e8db4efa914e60fb94d80114"));
            System.out.println(steamApiMapper.getSteamBdCountByKookId("1"));
//            System.out.println(steamApiMapper.addSteamBd("2","2","2"));
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void steamInfo() {
        SteamKook steamInfo = steamApiMapper.getSteamBdInfoByKookId("3568540449");
        if (null != steamInfo){
            Api apiInfoById = steamApiMapper.getApiInfoById("9e04d6e3e8db4efa914e60fb94d80114");
            String url = apiInfoById.getApiUrl() + "?key=" + apiInfoById.getAppKey() + "&steamid=" + steamInfo.getSteamId();
            Map<String, Object> res = OkHttpClientUtil.get(url);

            SteamResponseVo steamResponseVo = JSON.parseObject(res.get("response").toString(), SteamResponseVo.class);


            System.err.println("最近游玩游戏数：" + steamResponseVo.getTotalCount());

            steamResponseVo.getGames().forEach(item ->{

                Double playtime2weeks = Double.parseDouble(String.format("%.2f",Double.parseDouble(item.getPlaytime2weeks()) / 60));

                System.err.println("游戏名：" + item.getName());
                System.err.println("最近两周游戏时间：" + playtime2weeks);
                System.err.println("Windows游戏时间：" + Double.parseDouble(String.format("%.2f",Double.parseDouble(item.getPlaytimeWindowsForever()) / 60)));
                System.err.println("MAC游戏时间：" + Double.parseDouble(String.format("%.2f",Double.parseDouble(item.getPlaytimeMacForever()) / 60)));
                System.err.println("Linux游戏时间：" + Double.parseDouble(String.format("%.2f",Double.parseDouble(item.getPlaytimeLinuxForever()) / 60)));

                System.err.println("");
            });


        }else {
            log.info("请先绑定Steam！");
        }
    }


    @Test
    public void testSteamPer() {
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
            String timecreatedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(players.get("timecreated").toString()) * 1000));
            String lastlogoffDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(players.get("lastlogoff").toString()) * 1000));
            System.err.println(JSON.toJSONString(players));
            System.err.println("用户名：" + players.get("personaname").toString());
            System.err.println("状态：" + status);
            System.err.println("等级：" + levelResponse.get("player_level"));
            if (players.containsKey("gameextrainfo")){
                System.err.println("正在玩：" + players.get("gameextrainfo"));
            }
            System.err.println("最后登录时间：" + lastlogoffDate);
            System.err.println("账号创建时间：" + timecreatedDate);

        }else {
            System.err.println("请先绑定Steam！");
        }
    }



}
