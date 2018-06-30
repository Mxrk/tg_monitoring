package com.mxrk;

import com.mxrk.Monitoring.Monitor;
import com.mxrk.database.Database;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Database db = new Database();

        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try{
                    Monitor.checkHTTP();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, 0, 15000);

    }
}