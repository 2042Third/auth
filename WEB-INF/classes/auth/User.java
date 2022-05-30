package auth;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import util.JSONParse;
import util.userinfo;

/**
 * Handles a user's signin or sign up request
 * 
 * @author Yi Yang
 */
public class User {
  Date date = new Date();
  protected userinfo userinfo_;
  // protected String uemail;
  // protected String upass;
  protected PrintWriter out;
  Map<String, Object> json_data;
  protected String sender = "server";
  protected String user_type = "none";

  // Signin specific
  // protected String acreation = "";
  // protected String auser = "";
  // protected String aemail = "";
  // protected String areg_status = "";
  // protected String asession = "session-placeholder";

  // Signup specific
  // protected String uname = "";
  protected String from = "";

  /**
   * Set the user's output stream
   * 
   */
  public void set_out(PrintWriter a) {
    out = a;
  }

  /**
   * Respondes the user of the signin
   * 
   */
  protected Boolean respond_user() {
    userinfo_.status = "success";
    String res_str = JSONParse.json_request(user_type, sender, userinfo_);
    out.print(res_str);
    return true;
  }

  protected Boolean respond_user_fail() {
    String res_str = JSONParse.json_request(user_type, sender, "", "", "", "", "fail");
    out.print(res_str);
    return true;
  }

  protected boolean is_dynamic_web() {
    if (this.from.equals("pdm web")) {
      return true;
    } else {
      return false;
    }
  }

}
