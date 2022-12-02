package com.kook.pojo.steam;

import lombok.Data;

import java.util.List;

@Data
public class SteamResponseVo {

    //最近游玩数量
    private String totalCount;

    //游戏详情
    private List<SteamGamesVo> games;

}
