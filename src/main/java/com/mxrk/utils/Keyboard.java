package com.mxrk.utils;

import com.mxrk.database.Database;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    public InlineKeyboardMarkup listDomains(long userid){
        // count of domains
        int count = Database.countDomains(userid);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();
        List<InlineKeyboardButton> rowInline = new ArrayList<InlineKeyboardButton>();

        // Loop for Keyboard creation
        for (int i = 0; i < count; i++) {
            rowInline.add(new InlineKeyboardButton().setText(Database.get(userid).get(i).toString()).setCallbackData(Integer.toString(i)));
        }

        // Set the Keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

}
