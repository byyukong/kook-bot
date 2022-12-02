package com.kook.mapper;

import com.kook.pojo.Api;
import com.kook.pojo.SteamKook;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SteamApiMapper {

    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    Api getApiInfoById(String id);

    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    Integer getSteamBdCountByKookId(String kookId);

    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    SteamKook getSteamBdInfoByKookId(String kookId);

    Integer addSteamBd(@Param("id") String id,@Param("kookId") String kookId,@Param("steamId") String steamId);


}
