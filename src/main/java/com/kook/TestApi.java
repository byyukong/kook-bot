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
import sun.dc.pr.PRError;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

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



}
