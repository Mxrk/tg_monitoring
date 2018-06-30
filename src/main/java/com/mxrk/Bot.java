package com.mxrk;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class Bot extends TelegramLongPollingBot {


    public void onUpdateReceived(Update update) {

    }

    public String getBotUsername() {
        return Config.USER;
    }

    public String getBotToken() {
        return Config.TOKEN;
    }
}