package com.kook;

import com.kook.command.OkHttpClientUtil;

public class Test {
    public static void main(String[] args) {
        System.err.println(OkHttpClientUtil.getString("http://api.gt5.cc/api/dog"));
    }
}
