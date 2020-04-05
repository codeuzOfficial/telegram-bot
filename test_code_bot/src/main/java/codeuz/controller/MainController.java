package codeuz.controller;

import codeuz.Main;
import codeuz.dto.CodeMessage;
import codeuz.service.FileInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sun.management.Sensor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainController extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private GeneralController generalController;
    private FileInfoService fileInfoService;
    private TodoController todoController;

    public MainController() {
        this.generalController = new GeneralController();
        this.fileInfoService = new FileInfoService();
        this.todoController = new TodoController();
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();


        try {


            if (update.hasCallbackQuery()) {

                CallbackQuery callbackQuery = update.getCallbackQuery();
                String data = callbackQuery.getData();
                User user = callbackQuery.getFrom();
                message = callbackQuery.getMessage();

                LOGGER.info("messageId: " + message.getMessageId() + "  User_Name  " + user.getFirstName() + " User_username  " + user.getUserName() + "  message: " + data);

                if (data.equals("menu")) {
                    this.sendMsg(this.generalController.handle(data, message.getChatId(), message.getMessageId()));
                }
                if (data.startsWith("/todo")) {
                    this.sendMsg(this.todoController.handle(data, message.getChatId(), message.getMessageId()));
                }

            } else if (message != null) {
                String text = message.getText();

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                Integer messageId = message.getMessageId();
                User user = message.getFrom();

                LOGGER.info("messageId: " + messageId + "  User_Name  " + user.getUserName() + " User_username  " + user.getUserName() + "  message: " + text);


                if (text != null) {
                    if (text.equals("/start") || text.equals("/help") || text.equals("/setting")) {
                        this.sendMsg(this.generalController.handle(text, message.getChatId(), messageId));
                    } else if (this.todoController.getTodoItemStep().containsKey(message.getChatId()) || text.startsWith("/todo_")) {
                        this.sendMsg(this.todoController.handle(text, message.getChatId(), message.getMessageId()));
                    } else {
                        sendMessage.setText("Mavjud Emas");
                        this.sendMsg(sendMessage);
                    }
                } else {
                    this.sendMsg(this.fileInfoService.getFileInfo(message));
                }
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }


    }


    public void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(CodeMessage codeMessage) {
        try {
            switch (codeMessage.getType()) {
                case MESSAGE:
                    execute(codeMessage.getSendMessage());
                    break;
                case EDIT:
                    execute(codeMessage.getEditMessageText());
                    break;
                case MESSAGE_VIDEO:
                    execute(codeMessage.getSendMessage());
                    execute(codeMessage.getSendVideo());
                    break;
                default:
                    break;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "test_code_uz_bot";
    }

    @Override
    public String getBotToken() {
        return "1075603425:AAH9nXBs-JiH1g2wDHcWjDWo8oF0dOIUOk4";
    }
}
