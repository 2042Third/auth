package auth.signin;

import java.sql.ResultSet;

import auth.Codes;
import auth.User;
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
   * Respondes the user of the signin with the session key for next actions
   *
   */
  protected Boolean respond_user_with_sess(String userip) {
    userinfo_.status = "success";
    if (userinfo_.pass.length()>0){
      String sess = Codes.sess_rand_upw(userinfo_.pass); // generates the session key
      userinfo_.sess = sess;
      userinfo_.userIP = userip;
      System.out.printf("[Auth SignIn] User signin, %s, session ip \"%s\".\n",userinfo_.email,userinfo_.userIP);
      DataStart.u_userinfo_sess(userinfo_);
      userinfo_ = auserinfo_;
      userinfo_.sess = sess; // updates the latest userinfo with the new session key
      System.out.printf("[Auth SignIn] User signin success, %s, session key made \"%s\".\n",userinfo_.email,userinfo_.sess);
      respond_user();
    }
    return true;
  }
  /**
   * Connnects to the Database for checking if the given credentials exists in the
   * database; respondes accordingly to the outcome of the query.
   * 
   */
  public void check_login(String userip) {
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


        respond_user_with_sess(userip);
      } else {
        System.out.printf("[Auth SignIn] Unregistered user \"%s\" sign in\n", auserinfo_.name);
        userinfo_.statusInfo = "unregistered_user";
        respond_user_fail(); // from the next "else" statement
      }
      System.out.printf("[Auth SignIn] User sign in: email \"%s\", creation date \"%s\", user name : \"%s\"\n\n",
          auserinfo_.email,
          auserinfo_.creation_time, auserinfo_.name);
    } else {
      System.out.printf("[Auth SignIn] Failure User sign in: email \"%s\", pswd \"%s\"\n\n", userinfo_.email,
          userinfo_.pass);
      userinfo_.statusInfo = "does_not_exist";
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

  /**
   * Get information from database and checks the input userinfo.
   * @param au authenticating user
   * @return check status; boolean true for success, else false
   * */
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
        auserinfo_.sess = rs.getString("sess");
        System.out.printf("[Auth SignIn Check User login] query result: %s, %s, %s, %s\n",
            auserinfo_.name,
            auserinfo_.email,
            auserinfo_.creation_time,
            auserinfo_.reg_status);
        break;
      }
    } catch (Exception e) {
      System.out.println("[Auth SignIn Check User login] SQL no result in query or failure happened ");
    }
    return rt;
  }
}
