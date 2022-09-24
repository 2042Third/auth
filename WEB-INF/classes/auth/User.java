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
  protected Date date = new Date();
  protected userinfo userinfo_;
  protected PrintWriter out;
  protected Map<String, Object> json_data;
  protected String sender = "server";
  protected String user_type = "none";
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

  /**
   * Response to the incoming user a failure, not specifying the reason.
   * */
  protected Boolean respond_user_fail() {
    userinfo_.status = "fail";
    String res_str = JSONParse.json_request(user_type, sender, userinfo_);
    out.print(res_str);
    return true;
  }
  /**
   * Response to the incoming user a failure, including the reason.
   * @param reason the cause for the failure of the action
   * */
  protected Boolean respond_user_fail(String reason) {
    userinfo_.status = "fail";
    String res_str = JSONParse.json_request(user_type, sender, userinfo_);
    out.print(res_str);
    return true;
  }

  protected boolean is_dynamic_web() {
    return this.from.equals("pdm web");
  }

}
