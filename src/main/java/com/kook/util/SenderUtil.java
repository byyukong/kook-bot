package com.kook.util;

import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.MarkdownComponent;

public  class SenderUtil {

    public static void reply(User sender, Message message, String content) {
        ((TextChannelMessage) message).getChannel().sendComponent(
                new MarkdownComponent(content),
                (TextChannelMessage) message,null
        );
    }

    public static void reply(User sender, Message message, BaseComponent component) {
        if (message instanceof TextChannelMessage) {
            ((TextChannelMessage) message).getChannel().sendComponent(
                    component,
                    null, //(TextChannelMessage) message,
                    null
            );
        } else {
            sender.sendPrivateMessage(component);
        }
    }

}
