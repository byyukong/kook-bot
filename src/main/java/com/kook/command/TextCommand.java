package com.kook.command;

import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.plugin.BasePlugin;

/**
 * @author hy
 * @date 2022/12/2 16:42
 */


public class TextCommand extends BasePlugin {

    /**
     * 发送常规文字代码
     * @param command
     */
    public void sendText(String command,String content){
        new JKookCommand(command)
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                MultipleCardComponent card = new CardBuilder()
                                        .setTheme(Theme.NONE)
                                        .setSize(Size.LG)
                                        .addModule(
                                                new HeaderModule(new PlainTextElement(
                                                        content, false))
                                        )
                                        .build();
                                reply(sender, message, card);
                            } else {
                                getLogger().info("This command is not available for console.");
                                // 这个 else 块是可选的，但为了用户体验，最好还是提醒一下
                            }
                        }
                )
                .register();
    }

    private void reply(User sender, Message message, BaseComponent component) {
        if (message instanceof TextChannelMessage) {
            ((TextChannelMessage) message).getChannel().sendComponent(
                    component,
                    null,
                    null
            );
        } else {
            sender.sendPrivateMessage(component);
        }
    }
}
