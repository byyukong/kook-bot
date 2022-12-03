package com.kook.Instruction;

import com.kook.command.TextCommand;
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
public class EatInstruction {

    public static void getEat() {
        new JKookCommand("今天吃什么懒觉")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                MultipleCardComponent card = new CardBuilder()
                                        .setTheme(Theme.NONE)
                                        .setSize(Size.LG)
                                        .addModule(
                                                new HeaderModule(new PlainTextElement(
                                                        new TextCommand().chiShenMe(), false))
                                        )
                                        .build();
                                reply(sender, message, card);
                            }
                        }
                )
                .register();
    }

}
