package com.kook.Instruction;

import lombok.extern.slf4j.Slf4j;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.HeaderModule;

import static com.kook.util.SenderUtil.reply;

@Slf4j
/**
 * 查询用户信息
 */
public class KookInfoInstruction {

    /**
     * 查询用户信息
     */
    public static void getId() {
        new JKookCommand("getId")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                MultipleCardComponent card = new CardBuilder()
                                        .setTheme(Theme.NONE)
                                        .setSize(Size.LG)
                                        .addModule(
                                                new HeaderModule(new PlainTextElement(
                                                        "用户名：" + sender.getName() + "\n" + "ID：" + sender.getId(), false))
                                        )
                                        .build();
                                reply(sender, message, card);
                            } else {
                                log.info("This command is not available for console.");
                            }
                        }
                )
                .register();
    }

}
