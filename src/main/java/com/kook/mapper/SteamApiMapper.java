package com.kook.mapper;

import com.kook.pojo.Api;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SteamApiMapper {

    List<Api> getApiInfoById(String id);

}
