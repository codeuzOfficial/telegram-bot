package codeuz.service;

import codeuz.dto.CodeMessage;
import codeuz.enums.CodeMessageType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Video;

import java.util.List;

public class FileInfoService {

    public CodeMessage getFileInfo(Message message) { //< GETTING MESSAGE (WHICH CONTAINS FILE)


        Long cId = message.getChatId();

        CodeMessage codeMessage = new CodeMessage();
        codeMessage.setType(CodeMessageType.MESSAGE);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(cId);


        if (message.getPhoto() != null) { // ADDING PHOTO
            String s = this.show_photo_detail(message.getPhoto());
            sendMessage.setText(s);
        } else if (message.getVideo() != null) { // ADDING VIDEO
            String s = this.show_video_detail(message.getVideo());
            sendMessage.setText(s);
        } else {
            sendMessage.setText("NOT FOUND");
        }

        codeMessage.setSendMessage(sendMessage);
        return codeMessage;
    }

    private String show_photo_detail(List<PhotoSize> photoSizeList) {
        String s = "------------------------- PHOTO INFO -------------------------\n";
        for (PhotoSize photoSize : photoSizeList) {
            s += " Size = " + photoSize.getFileSize() + "  ,    ID  = " + photoSize.getFileId() + " \n";
        }
        return s;
    }

    private String show_video_detail(Video video) {
        String s = "------------------------- VIDEO INFO -------------------------\n";
        s += video.toString(); //" Size " + video.getFileSize() + "  ,  duration = " + video.getDuration() + "  ID = " + video.getMimeType();
        return s;
    }


}
