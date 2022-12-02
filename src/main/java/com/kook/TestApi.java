package com.kook;


import com.alibaba.fastjson.JSON;
import com.kook.mapper.SteamApiMapper;
import com.kook.util.MybatisUtils;
import com.kook.util.OkHttpClientUtil;
import com.kook.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import sun.dc.pr.PRError;

import javax.annotation.Resource;
import java.io.File;
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
//            System.out.println(steamApiMapper.getSteamBdInfoByKookId("1"));
            System.out.println(steamApiMapper.addSteamBd("2","2","2"));
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }



}
