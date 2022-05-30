package auth;

import java.sql.ResultSet;

import storage.DataStart;
import util.JSONParse;
import util.userinfo;

/**
 * Handles a user's Sign In request
 * 
 */
public class SignInUser extends User {
  private userinfo auserinfo_; // authenticated userinfo

  /**
   * SignInUser constructor
   */
  public SignInUser() {
    user_type = "SignIn";
    userinfo_ = new userinfo();
    auserinfo_ = new userinfo();
  }

  /**
   * Connnects to the Database for checking if the given credentials exists in the
   * database; respondes accordingly to the outcome of the query.
   * 
   */
  public void check_login() {
    if (userinfo_.pass.equals("") || userinfo_.email.equals("") || userinfo_.pass.equals("test")
        || userinfo_.email.equals("test")) {
      System.out.println("[Auth SignIn] " + date.getTime() + "");
      System.out.println("[Auth SignIn] User sign in failue: no email or password.");
      System.out.println("");
    }
    if (check_user_login(userinfo_)) {
      System.out.println("[Auth SignIn] " + date.getTime() + "");
      if (auserinfo_.reg_status.equals("1")) {
        System.out.printf("[Auth SignIn] Registered user \"%s\" sign in\n", auserinfo_.name);
        userinfo_ = auserinfo_;
        respond_user();
      } else {
        System.out.printf("[Auth SignIn] Unregistered user \"%s\" sign in\n", auserinfo_.name);
      }
      System.out.printf("[Auth SignIn] User sign in: email \"%s\", creation date \"%s\", user name : \"%s\"\n\n",
          auserinfo_.email,
          auserinfo_.creation_time, auserinfo_.name);
    } else {
      System.out.printf("[Auth SignIn] Failure User sign in: email \"%s\", pswd \"%s\"\n\n", userinfo_.email,
          userinfo_.pass);
      respond_user_fail();
    }
  }

  /**
   * Parses the incoming json data
   * 
   * @param data json string
   */
  public void parse_json(String data) {
    json_data = JSONParse.parse(data);

    userinfo_.pass = (String) json_data.get("upw");
    userinfo_.email = (String) json_data.get("umail");
    System.out.println("[Auth " + user_type + "] " + date.getTime() + "");
    System.out.printf("[Auth %s] User try sign in: email \"%s\"\n", user_type, userinfo_.email);

  }

  private Boolean check_user_login(userinfo au) {
    ResultSet rs = DataStart.q_userinfo_login(au);
    Boolean rt = false;
    try {
      while (rs.next()) {
        rt = true;
        auserinfo_.name = rs.getString("name");
        auserinfo_.email = rs.getString("email");
        auserinfo_.creation_time = rs.getString("creation");
        auserinfo_.reg_status = rs.getString("registered");
        System.out.printf("[Auth SignIn Check User login] query result: %s, %s, %s, %s\n",
            auserinfo_.name,
            auserinfo_.email,
            auserinfo_.creation_time,
            auserinfo_.reg_status);

      }
    } catch (Exception e) {
      System.out.println("[Auth SignIn Check User login] SQL no result in query or failure happened ");
    }
    return rt;
  }
}
