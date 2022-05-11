package storage;
import java.util.prefs.*;
import util.SendMail;
import java.net.URLDecoder;
import java.sql.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.io.File;

public class DataStart {

    public static ResultSet q_userinfo_reg ( String reg_key){
        Date date = new Date();
        // Preferences node = Preferences.userNodeForPackage(this.getClass());
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e){
            System.out.println("postgresql driver not found.");
        }
        String url = "jdbc:postgresql://localhost:5432/pdm";
        try{
            Connection con = DriverManager.getConnection(
                url, 
                "pdmsecurity", 
                "16a93646e026f05c4b497e14c921d6b9915263aaa64663039dba8f13181f15e3");
            String query = "select name, email, creation from userinfo where register_key = ?";
            PreparedStatement stat = con.prepareStatement(query);
            stat.setString(1, reg_key);

            System.out.println("[web_notes storage] success query for db");
            // ResultSet rs = stat.executeQuery();
            return stat.executeQuery();
            
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Opening connection unsuccessful!");
        }
        return null;
    }
    public static ResultSet q_userinfo_login ( String uemail, String upass){
        Date date = new Date();
        // Preferences node = Preferences.userNodeForPackage(this.getClass());
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e){
            System.out.println("postgresql driver not found.");
        }
        String url = "jdbc:postgresql://localhost:5432/pdm";
        try{
            Connection con = DriverManager.getConnection(
                url, 
                "pdmsecurity", 
                "16a93646e026f05c4b497e14c921d6b9915263aaa64663039dba8f13181f15e3");
            System.out.printf("[postgresql] checking signin for email: %s, pssword: %s\n",uemail, upass);
            String query = "select name, email, creation, registered from userinfo where email = ? and spw = ?;";
            PreparedStatement stat = con.prepareStatement(query);
            stat.setString(1, uemail);
            stat.setString(2, upass);

            // System.out.println("[web_notes storage] (login) success query for db");
            // ResultSet rs = stat.executeQuery();
            return stat.executeQuery();
            
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Opening connection unsuccessful!");
        }
        return null;
    }

    /**
     * Update the database for registration status
     * */
    public static void u_userinfo_reg ( String uemail){
        Date date = new Date();
        // Preferences node = Preferences.userNodeForPackage(this.getClass());
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e){
            System.out.println("postgresql driver not found.");
        }
        String url = "jdbc:postgresql://localhost:5432/pdm";
        try{
            Connection con = DriverManager.getConnection(
                url, 
                "pdmsecurity", 
                "16a93646e026f05c4b497e14c921d6b9915263aaa64663039dba8f13181f15e3");
            String query = "update userinfo set registered = ? where email = ?;";
            PreparedStatement stat = con.prepareStatement(query);
            stat.setString(1, "1");
            stat.setString(2, uemail);

            System.out.println("[web_notes storage] update registration status for user ");
            // ResultSet rs = stat.executeQuery();
            stat.executeUpdate();
            
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Opening connection unsuccessful!");
        }
    }

    /**
     * Update the database for registration status
     * */
    public static ResultSet u_userinfo_check ( String uemail){
        Date date = new Date();
        // Preferences node = Preferences.userNodeForPackage(this.getClass());
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e){
            System.out.println("postgresql driver not found.");
        }
        String url = "jdbc:postgresql://localhost:5432/pdm";
        try{
            Connection con = DriverManager.getConnection(
                url, 
                "pdmsecurity", 
                "16a93646e026f05c4b497e14c921d6b9915263aaa64663039dba8f13181f15e3");
            System.out.printf("[postgresql] checking signin for email: %s\n",uemail);
            String query = "select 1 from userinfo where email = ?;";
            PreparedStatement stat = con.prepareStatement(query);
            stat.setString(1, uemail);
            return stat.executeQuery();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Opening connection unsuccessful!");
        }
        return null;
    }
    public static void register_user (String uname, String umail, String upw, String prod, String reg_key){
        Date date = new Date();
        // Preferences node = Preferences.userNodeForPackage(this.getClass());
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e){
            System.out.println("postgresql driver not found.");
        }
        String url = "jdbc:postgresql://localhost:5432/pdm";
        try{
            Connection con = DriverManager.getConnection(
                url, 
                "pdmsecurity", 
                "16a93646e026f05c4b497e14c921d6b9915263aaa64663039dba8f13181f15e3");
            String query = "INSERT INTO userinfo(name, spw, creation, product, email, register_key, logs) VALUES(?, ?, ?, ?, ?, ?, ?) ON CONFLICT (email) DO UPDATE SET register_key = ?;";
            // String query = "INSERT INTO userinfo(name, spw, creation, product, email, register_key, logs) VALUES(?, ?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO UPDATE SET txt = EXCLUDED.txt;";
            PreparedStatement stat = con.prepareStatement(query);
            stat.setString(1, uname);
            stat.setString(2, upw);
            stat.setString(3, date.getTime()+"");
            stat.setString(4, prod);
            stat.setString(5, umail);
            stat.setString(6, reg_key);
            stat.setString(7, "testing user, not production code...");
            stat.setString(8, reg_key);
            // ResultSet rs = stat.executeQuery();
            stat.executeUpdate();
            
            System.out.println("[web_notes storage] success registeration for db");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Opening connection unsuccessful!");
        }
    }
}