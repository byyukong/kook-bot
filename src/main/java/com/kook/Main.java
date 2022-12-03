package com.kook;

// import 被忽略

import com.kook.Instruction.*;
import com.kook.mapper.SteamApiMapper;
import com.kook.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import snw.jkook.plugin.BasePlugin;

import static com.kook.Instruction.SteamInstruction.bindSteam;
import static com.kook.Instruction.SteamInstruction.getStringInfo;

public class Main extends BasePlugin {

    private SqlSession sqlSession = MybatisUtils.getSqlSession();
    private SteamApiMapper steamApiMapper = sqlSession.getMapper(SteamApiMapper.class);

    @Override
    public void onLoad() {
        // 重写 onLoad 并不是必须的，但是您可以在此阶段做一些初始化工作，比如解压默认的配置文件 (saveDefaultConfig)1
        // 三个方法内的日志记录也不是必须的，但是善用日志记录有助于描述 Bot 的运行状态！
        getLogger().info("PingBot 加载完成！"); // getLogger() 返回的是一个 org.slf4j.Logger 的实例对象，为日志记录器
    }

    @Override
    public void onEnable() {

        /**
         * /hito
         * 查询一言
         */
        HitoInstruction.getHito();

        /**
         * /赵腾鹏
         * 骂tp
         */
        TpInstruction.getTp();

        /**
         * /getId
         * 查询用户信息
         */
        KookInfoInstruction.getId();

        /**
         * /weather
         * 查询天气
         */
        WeatherInstruction.getWeather();

        /**
         * /舔狗
         * 查询舔狗语录
         */
        DogInstruction.getDog();

        /**
         * /社会
         * 查询社会语录
         */
        SocietyInstruction.getSociety();

        /**
         * /steambd
         * 绑定Steam
         */
        bindSteam();

        /**
         * /steaminfo
         * 查询Steam信息
         */
        getStringInfo();

        /**
         * /摸鱼
         * 查询摸鱼日报
         */
        FishInstruction.getFish();

        /**
         * /今天吃什么懒觉
         * 查询今天吃什么
         */
        EatInstruction.getEat();

        getLogger().info("PingBot 启动成功！");
    }

    @Override
    public void onDisable() {
        getLogger().info("PingBot 已停止！");
    }
}
