package codeuz.dto;

import codeuz.enums.CodeMessageType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class CodeMessage {
    public CodeMessageType type;

    private SendMessage sendMessage;
    private EditMessageText editMessageText;
    private SendVideo sendVideo;


    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public EditMessageText getEditMessageText() {
        return editMessageText;
    }

    public void setEditMessageText(EditMessageText editMessageText) {
        this.editMessageText = editMessageText;
    }

    public CodeMessageType getType() {
        return type;
    }

    public void setType(CodeMessageType type) {
        this.type = type;
    }

    public SendVideo getSendVideo() {
        return sendVideo;
    }

    public void setSendVideo(SendVideo sendVideo) {
        this.sendVideo = sendVideo;
    }
}
