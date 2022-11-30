package com.pojo.music;

import lombok.Data;

/**
 * @author hy
 * @date 2022/11/30 14:27
 */

@Data
public class CommonParameters {
    private String appId;
    private String signType;
    private String sign;
    private Long timestamp;
    private String bizContent;
    private String accessToken;
    private String encryptType;
    private String device;
}
