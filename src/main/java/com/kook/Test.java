package com.kook;

import com.kook.command.OkHttpClientUtil;

public class Test {
    public static void main(String[] args) {
//        System.err.println(OkHttpClientUtil.getString("http://api.gt5.cc/api/dog"));
        String res = OkHttpClientUtil.getString("https://api.oick.cn/yulu/api.php");
        System.err.println(res);
        System.err.println(res.substring(1,res.length() -1));
    }
}
