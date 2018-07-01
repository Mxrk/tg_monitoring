package com.mxrk.database;

import com.mxrk.utils.Validate;

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

           CREATE TABLE `users` ( `userID` INTEGER, PRIMARY KEY(`userID`) )
           CREATE TABLE subs( userID INTEGER, domainID INTEGER, FOREIGN KEY(userID) REFERENCES users(userID) FOREIGN KEY(domainID) REFERENCES domains(domainID) )
           CREATE TABLE `domains` ( `domainID` INTEGER PRIMARY KEY AUTOINCREMENT, `status` INTEGER, `url` TEXT )


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
            String nsql = "SELECT * FROM domains INNER JOIN subs ON subs.domainID=domains.domainID WHERE subs.userID = ?";
            //String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement st = c.prepareStatement(nsql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String domain = rs.getString("url");
                //  text.append(i + " " + domain + "\n");
                System.out.println(domain);
                list.add(domain);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int countDomains(long id) {

        try {
            stmt = c.createStatement();
            String sql = "SELECT count(*) FROM subs WHERE userID = ?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean add(long id, String domain) {
        String url = Validate.domain(domain);
        if(domainExists(url)){
            return false;
        }else {
            addUser(id);

            String sql = "INSERT INTO domains(url, status) VALUES(?,?)";
            try {
                PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, url);
                st.setInt(2, 0);
                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                linkDomain(id,rs.getInt(1));
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;

            }
        }
    }
    public static ArrayList getAll() {
        ArrayList<String> list = new ArrayList<String>();

        // Todo: if empty, return 0
        try {
            stmt = c.createStatement();
            String sql = "SELECT * FROM domains";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String domain = rs.getString("url");
                list.add(domain);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void check(boolean check, String domain) {
        if(check){
            String sql = "UPDATE domains SET status = '1' WHERE url = ?";
            try {
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setString(1, domain); // set input parameter 1
                pstmt.executeUpdate(); // execute update statement
               // c.commit();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }else{
            try {
                String sql = "UPDATE domains SET status = '0' WHERE url = ?";
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setString(1, domain); // set input parameter 1
                pstmt.executeUpdate(); // execute update statement
                //c.commit();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }

        }
    }
    public static boolean domainExists(String domain) {
        try {
            stmt = c.createStatement();
            String sql = "select count(1) from domains where url = ?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, domain);
            ResultSet rs = st.executeQuery();
            int count = rs.getInt(1);
            if(count == 0){
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void addUser(long userid){
        String sql = "INSERT if not exists INTO users(userID) VALUES(?)";
        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setLong(1, userid);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void linkDomain(long userID, int domainID){
        String sql = "INSERT if not exists INTO users(userID, domainID) VALUES(?,?)";
        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setLong(1, userID);
            st.setInt(2,domainID);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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
