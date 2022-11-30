package com.pojo.weather;

import lombok.Data;

@Data
public class LocationVo {

    private String id;
    private String name;
    private String country;
    private String path;
    private String timezone;
    private String timezone_offset;

}
