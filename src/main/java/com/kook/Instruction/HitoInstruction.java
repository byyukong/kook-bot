package com.kook.Instruction;

import com.kook.util.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;

import java.util.Map;

import static com.kook.util.SenderUtil.reply;

/**
 * 查询一言
 */
@Slf4j
public class HitoInstruction {

    public static void getHito(){
        new JKookCommand("hito")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) { // 确保是个 Kook 用户在执行此命令
                                Map<String,Object> map = OkHttpClientUtil.get("https://v1.hitokoto.cn/");
                                reply(sender, message, map.get("hitokoto").toString());
                            } else {
                                log.info("This command is not available for console.");
                                // 这个 else 块是可选的，但为了用户体验，最好还是提醒一下
                                // 另外，我们假设此执行器是在 Bot#onEnable 里写的，所以我们可以使用 getLogger() 。
                            }
                        }
                )
                .register();
    }

}
