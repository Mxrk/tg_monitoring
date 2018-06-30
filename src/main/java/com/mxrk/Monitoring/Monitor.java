package com.mxrk.Monitoring;

import com.mxrk.database.Database;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Monitor {
    public static void checkHTTP() throws IOException {
        ArrayList<String> list= Database.getAll();
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
            URL siteURL = new URL(list.get(i));
            HttpURLConnection connection = (HttpURLConnection)siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            if (code == 200){
                Database.check(true, list.get(i));
            }else {
                Database.check(false, list.get(i));
            }
        }
    }

}
