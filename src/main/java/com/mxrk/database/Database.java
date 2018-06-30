package com.mxrk.database;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    static Connection c = null;
    static Statement stmt = null;

    public Database() {
        try {
            // Choose the driver
            Class.forName("org.sqlite.JDBC");
            //Set the path -> if no db found it will create one
            c = DriverManager.getConnection("jdbc:sqlite:DB.db");
            System.out.println("Connected to DB");
            /*
            TODO: Setup DB

           CREATE TABLE `user` ( `id` INTEGER, `domain` TEXT, PRIMARY KEY(`domain`) )

            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList get(long id) {
        //  StringBuilder text = new StringBuilder();
        ArrayList<String> list = new ArrayList<String>();

        // Todo: if empty, return 0
        try {
            stmt = c.createStatement();
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            int i = 1;
            while (rs.next()) {
                String domain = rs.getString("domain");
                //  text.append(i + " " + domain + "\n");
                list.add(domain);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int countDomains(long id) {
        int i = 0;
        try {
            stmt = c.createStatement();
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static boolean add(long id, String domain) {
        String sql = "INSERT INTO user(id,domain) VALUES(?,?)";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setLong(1, id);
            st.setString(2, domain);
            st.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
