package com.kook.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SteamApiMapper {

    List<Map<String,Object>> queryRole();

}
