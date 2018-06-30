package com.mxrk;

import com.mxrk.database.Database;

import com.mxrk.utils.keyboard;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {


    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long userid = update.getMessage().getChat().getId();
            String text;
            if (update.getMessage().getText().startsWith("/add")) {
                String[] raw = update.getMessage().getText().split(" ", 2);

                boolean add = Database.add(userid, raw[1]);
                if (add) {
                    text = raw[1] + " added.";
                } else {
                    text = "domain already exists";
                }
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(text);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (update.getMessage().getText().equals("/get")) {
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId());
                //get domains of user
                message.setText("Here are your monitored domains:");

                keyboard k = new keyboard();
                message.setReplyMarkup(k.listDomains(userid));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBotUsername() {
        return Config.USER;
    }

    public String getBotToken() {
        return Config.TOKEN;
    }
}