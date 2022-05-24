package storage;

import util.*;
import java.sql.*;
import java.util.Date;

/**
 * Databse interface for pdm
 * 
 * @author Yi Yang
 * 
 */
public class DataStart {
  private static String dbstorel = "jdbc:postgresql://localhost:5432/pdm";
  private static String dbstoren = "pdmsecurity";
  private static String dbstorep = "16a93646e026f05c4b497e14c921d6b9915263aaa64663039dba8f13181f15e3";
  private static String dbstored = "org.postgresql.Driver";

  /**
   * Query the user info when register
   * 
   * @param reg_key the registration key
   * @return a ResultSet
   */
  public static ResultSet q_userinfo_reg(String reg_key) {
    // Preferences node = Preferences.userNodeForPackage(this.getClass());
    try {
      Class.forName(dbstored);
    } catch (Exception e) {
      System.out.printf(" \"%s\" driver not found.\n", dbstored);
    }
    try {
      Connection con = DriverManager.getConnection(
          dbstorel,
          dbstoren,
          dbstorep);
      String query = Queries.q_userinfo_reg;
      PreparedStatement stat = con.prepareStatement(query);
      stat.setString(1, reg_key);

      System.out.println("[web_notes storage] success query for db");
      // ResultSet rs = stat.executeQuery();
      return stat.executeQuery();

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Opening connection unsuccessful!");
    }
    return null;
  }

  /**
   * Query the user info when login
   * 
   * @param uemail user email
   * @param upass  user password
   * @return a ResultSet with user name, email, account creation time,
   *         registration status
   */
  public static ResultSet q_userinfo_login(String uemail, String upass) {
    // Preferences node = Preferences.userNodeForPackage(this.getClass());
    try {
      Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      System.out.println("postgresql driver not found.");
    }
    try {
      Connection con = DriverManager.getConnection(
          dbstorel,
          dbstoren,
          dbstorep);
      System.out.printf("[postgresql] checking signin for email: %s and input pssword\n", uemail);
      String query = Queries.q_userinfo_login;
      PreparedStatement stat = con.prepareStatement(query);
      stat.setString(1, uemail);
      stat.setString(2, upass);
      return stat.executeQuery();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Opening connection unsuccessful!");
    }
    return null;
  }

  /**
   * Update the database for registration status to True for a user.
   * 
   * @param uemail user email
   */
  public static void u_userinfo_reg(String uemail) {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      System.out.println("postgresql driver not found.");
    }
    try {
      Connection con = DriverManager.getConnection(
          dbstorel,
          dbstoren,
          dbstorep);
      String query = Queries.u_userinfo_reg;
      PreparedStatement stat = con.prepareStatement(query);
      stat.setString(1, "1");
      stat.setString(2, uemail);

      System.out.println("[web_notes storage] update registration status for user ");
      // ResultSet rs = stat.executeQuery();
      stat.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Opening connection unsuccessful!");
    }
  }

  /**
   * Query the database if the email has already been registered
   * 
   * @param uemail user email
   * @return ResultSet with the query result for this input email
   */
  public static ResultSet q_userinfo_check(String uemail) {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      System.out.println("postgresql driver not found.");
    }
    try {
      Connection con = DriverManager.getConnection(
          dbstorel,
          dbstoren,
          dbstorep);
      System.out.printf("[postgresql] checking signin for email: %s\n", uemail);
      String query = Queries.q_userinfo_check;
      PreparedStatement stat = con.prepareStatement(query);
      stat.setString(1, uemail);
      return stat.executeQuery();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Opening connection unsuccessful!");
    }
    return null;
  }

  /**
   * Updates the db when new user is added
   * 
   * @param uname   user name
   * @param umail   user email
   * @param upw     user password
   * @param prod    creation time
   * @param reg_key registration key
   */
  public static void register_user(String uname, String umail, String upw, String prod, String reg_key) {
    Date date = new Date();
    // Preferences node = Preferences.userNodeForPackage(this.getClass());
    try {
      Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      System.out.println("postgresql driver not found.");
    }
    try {
      Connection con = DriverManager.getConnection(
          dbstorel,
          dbstoren,
          dbstorep);
      String query = Queries.register_user;

      PreparedStatement stat = con.prepareStatement(query);
      stat.setString(1, uname);
      stat.setString(2, upw);
      stat.setString(3, date.getTime() + "");
      stat.setString(4, prod);
      stat.setString(5, umail);
      stat.setString(6, reg_key);
      stat.setString(7, "testing user, not production code...");
      stat.setString(8, reg_key);
      // ResultSet rs = stat.executeQuery();
      stat.executeUpdate();

      System.out.println("[web_notes storage] success registeration for db");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Opening connection unsuccessful!");
    }
  }

  /**
   * Inserts a new note into the db for a user.
   * Correspondes to each new note .
   * '''
   * insert into notes(userid, content, h, intgrh)
   * select u.id , UCONTENT , UHASH, INTHASH from
   * userinfo u
   * where u.email = UEMAIL;
   * '''
   * 
   * @param uemail   The user email
   * @param ucontent The content of the note
   * @param uhash    Hash of the unencrypted note
   * @return ResultSet that contain the id of the newly create note.
   */
  public static ResultSet u_notes_new(String uemail, String ucontent, String uhash) {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      System.out.println("postgresql driver not found.");
    }
    try {
      Connection con = DriverManager.getConnection(
          dbstorel,
          dbstoren,
          dbstorep);
      String query = Queries.u_notes_new;
      PreparedStatement stat = con.prepareStatement(query);
      stat.setString(1, ucontent);
      stat.setString(2, uhash);
      stat.setString(3, SHA3.get_sha3A(ucontent));
      stat.setString(4, uemail);

      System.out.printf("[web_notes storage notes] new note added for user email \"%s\"\n", uemail);
      return stat.executeQuery();
      // stat.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Opening connection unsuccessful or new note creation failure!");
    }
    return null;
  }

  /**
   * Updates a existing note, where the client has the id of the note
   * 
   * @param ucontent The content of the note
   * @param uhash    Hash of the unencrypted note
   * @param unoteid  ID of the note being updated
   */
  public static void u_notes_update(String ucontent, String uhash, String unoteid) {
    try {
      Class.forName(dbstored);
    } catch (Exception e) {
      System.out.println("postgresql driver not found.");
    }
    try {
      Connection con = DriverManager.getConnection(
          dbstorel,
          dbstoren,
          dbstorep);
      String query = Queries.u_notes_update;
      PreparedStatement stat = con.prepareStatement(query);
      stat.setString(1, ucontent);
      stat.setString(2, uhash);
      stat.setString(3, SHA3.get_sha3A(ucontent));
      stat.setString(4, unoteid);

      System.out.printf("[web_notes storage notes] notes updated for noteid \"%s\"\n", unoteid);
      // ResultSet rs = stat.executeQuery();
      stat.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Opening connection unsuccessful or update existing note failure!");
    }
  }
}