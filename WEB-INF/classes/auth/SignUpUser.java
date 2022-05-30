package auth;

import java.sql.ResultSet;

import storage.DataStart;
import util.JSONParse;
import util.SendMail;
import util.userinfo;

public class SignUpUser extends User {

  protected String bad_dir = "";

  public SignUpUser() {
    user_type = "SignUp";
    userinfo_ = new userinfo();
    userinfo_.user_type = "pdm web";
  }

  /**
   * Parses the incoming json data
   * 
   * @param data json string
   */
  public void parse_json(String data) {
    json_data = JSONParse.parse(data);

    userinfo_.name = (String) json_data.get("uname");
    from = (String) json_data.get("type");
    userinfo_.pass = (String) json_data.get("upw");
    userinfo_.email = (String) json_data.get("umail");
    System.out.println("[Auth " + user_type + "] " + date.getTime() + "");
    System.out.printf("[Auth %s] User try sign up: email \"%s\"\n", user_type, userinfo_.email);
    System.out.println("parse end");

  }

  public void resolve_singup() {
    System.out.printf("[Auth %s] User register: name \"%s\", email \"%s\", password \"%s\"\n", user_type,
        userinfo_.name,
        userinfo_.email,
        userinfo_.pass);
    String reg_key = "";
    try {
      reg_key = Codes.byte_rand512(userinfo_.name, userinfo_.email);
    } catch (Exception e) {
      System.out.println("[Auth Sign Up] hash function for registration key failed.");
      return;
    }
    if (check_user_exist(userinfo_.email)) {
      respond_user_fail();
    } else {
      respond_user();

      DataStart.register_user(userinfo_);
      SendMail sm = new SendMail();
      bad_dir = bad_dir.substring(0, bad_dir.lastIndexOf("/"));
      sm.send_reg(userinfo_.email,
          userinfo_.name, "https://pdm.pw/auth/try/" + reg_key, bad_dir + "/resc/email_link.html");
    }
    return;
  }

  /**
   * @param a real path of the current server
   * 
   */
  public void set_bad_dir(String a) {
    bad_dir = a.substring(0, a.lastIndexOf("/"));
  }

  /**
   * Checks the existence of a user using this email.
   * 
   * @param uemail user email
   * 
   */
  private Boolean check_user_exist(String uemail) {
    ResultSet rs = DataStart.q_userinfo_check(userinfo_);
    Boolean rt = false;
    try {
      while (rs.next()) {
        rt = true;
        System.out.printf("[Auth Sign Up] query result: user exists\n");
      }
    } catch (Exception e) {
      System.out.println("[Auth Sign Up] SQL no result in query or failure happened ");
    }
    return rt;
  }
}
