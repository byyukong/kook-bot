package com.kook.Instruction;

import com.kook.util.OkHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;

import static com.kook.util.SenderUtil.reply;

/**
 * 查询社会语录
 */
@Slf4j
public class SocietyInstruction {

    public static void getSociety() {
        new JKookCommand("社会")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                String res = OkHttpClientUtil.getString("https://api.oick.cn/yulu/api.php");


                                if (null != res) {
                                    MultipleCardComponent card = new CardBuilder()
                                            .setTheme(Theme.NONE)
                                            .setSize(Size.LG)
                                            .addModule(
                                                    new ContextModule.Builder()
                                                            .add(new PlainTextElement(res.substring(1,res.length() -1), false)).build()
                                            )
                                            .build();
                                    reply(sender, message, card);
                                } else {
                                    reply(sender, message, "请求错误！");
                                }

                            } else {
                                log.info("This command is not available for console.");
                            }
                        }
                )
                .register();
    }

}
