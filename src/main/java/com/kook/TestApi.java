package com.kook;


import com.alibaba.fastjson.JSON;
import com.kook.mapper.SteamApiMapper;
import com.kook.util.MybatisUtils;
import com.kook.util.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TestApi {
    SqlSession sqlSession = MybatisUtils.getSqlSession();


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
    public void sqlTest(){
        //获得SqlSession对象
        try {
            SteamApiMapper mapper = sqlSession.getMapper(SteamApiMapper.class);
            System.out.println(mapper.queryRole());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

}
