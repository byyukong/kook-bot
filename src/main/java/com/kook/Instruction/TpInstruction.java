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
public class TpInstruction {

    public static void getTp() {
        new JKookCommand("赵腾鹏")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                MultipleCardComponent card = new CardBuilder()
                                        .setTheme(Theme.NONE)
                                        .setSize(Size.LG)
                                        .addModule(
                                                new HeaderModule(new PlainTextElement("笨逼！", false))
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
