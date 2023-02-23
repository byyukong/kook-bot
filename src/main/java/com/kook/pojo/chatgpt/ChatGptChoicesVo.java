package com.kook.pojo.chatgpt;

import lombok.Data;

import java.util.List;

@Data
public class ChatGptChoicesVo {

    private String text;
    private String index;
    private String logprobs;
    private String finish_reason;

    private List<ChatGptChoicesVo> choices;

}
