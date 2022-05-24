package auth;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;

import storage.DataStart;
import util.JSONParse;

/**
 * Handles a user's Sign In request
 * 
 */
public class SignInUser extends User {
  Date date = new Date();
  private PrintWriter out;
  private String acreation = "";
  private String auser = "";
  private String aemail = "";
  private String areg_status = "";
  private String asession = "session-placeholder";

  Map<String, Object> json_data;

  /**
   * Set the user's output stream
   * 
   */
  public void set_out(PrintWriter a) {
    out = a;
  }

  /**
   * Parses the incoming json data
   * 
   * @param data json string
   */
  public void parse_json(String data) {
    json_data = JSONParse.parse(data);

    upass = (String) json_data.get("upw");
    uemail = (String) json_data.get("umail");
    System.out.println("[Auth SignIn] " + date.getTime() + "");
    System.out.printf("[Auth SignIn] User try sign in: email \"%s\"\n\n", uemail);

  }

  /**
   * Connnects to the Database for checking if the given credentials exists in the
   * database; respondes accordingly to the outcome of the query.
   * 
   */
  public void check_login() {
    if (upass.equals("") || uemail.equals("") || upass.equals("test") || uemail.equals("test")) {
      System.out.println("[Auth SignIn] " + date.getTime() + "");
      System.out.println("[Auth SignIn] User sign in failue: no email or password.");
      System.out.println("");
    }
    if (check_user_login(uemail, upass)) {
      System.out.println("[Auth SignIn] " + date.getTime() + "");
      if (areg_status.equals("1")) {
        System.out.printf("[Auth SignIn] Registered user \"%s\" sign in\n", auser);
        respond_user();
      } else {
        System.out.printf("[Auth SignIn] Unregistered user \"%s\" sign in\n", auser);
      }
      System.out.printf("[Auth SignIn] User sign in: email \"%s\", creation date \"%s\", user name : \"%s\"\n\n",
          aemail,
          acreation, auser);
    } else {
      System.out.printf("[Auth SignIn] Failure User sign in: email \"%s\", pswd \"%s\"\n\n", uemail, upass);
      respond_user_fail();
    }
  }

  private Boolean respond_user() {
    String res_str = JSONParse.json_request("login", "server", auser, acreation, aemail, asession, "success");
    out.print(res_str);
    return true;
  }

  private Boolean respond_user_fail() {
    String res_str = JSONParse.json_request("login", "server", "", "", "", "", "fail");
    out.print(res_str);
    return true;
  }

  private Boolean check_user_login(String uemail, String upass) {
    ResultSet rs = DataStart.q_userinfo_login(uemail, upass);
    Boolean rt = false;
    try {
      while (rs.next()) {
        rt = true;
        auser = rs.getString("name");
        aemail = rs.getString("email");
        acreation = rs.getString("creation");
        areg_status = rs.getString("registered");
        System.out.printf("[Auth SignIn Check User login] query result: %s, %s, %s, %s\n", auser, aemail, acreation,
            areg_status);

      }
    } catch (Exception e) {
      System.out.println("[Auth SignIn Check User login] SQL no result in query or failure happened ");
    }
    return rt;
  }
}
