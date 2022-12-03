package com.kook.Instruction;

import com.kook.util.OkHttpClientUtil;
import com.kook.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import snw.jkook.JKook;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.User;
import snw.jkook.entity.abilities.Accessory;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.ImageElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;

import java.io.File;
import java.util.Map;

import static com.kook.util.SenderUtil.reply;

/**
 * 查询摸鱼日报
 */
@Slf4j
public class FishInstruction {

    public static void getFish() {
        new JKookCommand("摸鱼")
                .executesUser(
                        (sender, args, message) -> {
                            if (sender instanceof User) {
                                Map<String,Object> res = OkHttpClientUtil.get("https://api.vvhan.com/api/moyu?type=json");
                                try {
                                    File file = PictureUtils.UrlToFile(res.get("url").toString());
                                    MultipleCardComponent card = new CardBuilder()
                                            .setTheme(Theme.NONE)
                                            .setSize(Size.LG)
                                            .addModule(new HeaderModule(new PlainTextElement("!!!", false)))
                                            .addModule(new SectionModule(new MarkdownElement("摸鱼图片:")
                                                    ,file != null ? new ImageElement(
                                                    JKook.getHttpAPI().uploadFile(file),
                                                    null,
                                                    Size.SM,
                                                    false
                                            ):null,
                                                    file !=null ? Accessory.Mode.RIGHT : null))
                                            .build();
                                    reply(sender, message, card);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                log.info("This command is not available for console.");
                            }
                        }
                )
                .register();
    }

}
