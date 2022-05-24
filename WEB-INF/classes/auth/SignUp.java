package auth;

import util.*;
import storage.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import jakarta.servlet.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUp extends HttpServlet {
  private String uname = "";
  private String upw = "";
  private String umail = "";
  private String from = "";
  private PrintWriter out;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    Map<String, Object> json_data;

    response.setContentType("text/html");
    out = response.getWriter();
    request.getSession(true);

    String data = read_stream(request.getInputStream());
    System.out.println(data);

    json_data = JSONParse.parse(data);

    uname = (String) json_data.get("uname");
    upw = (String) json_data.get("upw");
    umail = (String) json_data.get("umail");
    from = (String) json_data.get("type");
    System.out.printf("[Auth Sign Up] User register: name \"%s\", email \"%s\", password \"%s\"\n", uname, umail, upw);
    String reg_key = "";
    try {
      reg_key = Codes.byte_rand512(uname, umail);
    } catch (Exception e) {
      System.out.println("[Auth Sign Up] hash function for registration key failed.");
      return;
    }
    if (check_user_exist(umail)) {
      respond_user_fail();

    } else {
      respond_user();
      DataStart.register_user(uname, umail, upw, "pdm static web", reg_key);
      SendMail sm = new SendMail();
      final ServletContext servletContext = getServletContext();
      String bad_dir = servletContext.getRealPath(servletContext.getContextPath());
      bad_dir = bad_dir.substring(0, bad_dir.lastIndexOf("/"));
      sm.send_reg(umail, uname, "https://pdm.pw/auth/try/" + reg_key, bad_dir + "/resc/email_link.html");
    }
    return;
    // out.println(EmbedHTML.plain("/auth","click the link in email \""+umail+"\" to
    // finish the registration "));
  }

  private Boolean respond_user() {
    String res_str = JSONParse.json_request("signup", "server", uname, "", umail, "", "success");
    out.print(res_str);
    return true;
  }

  private Boolean respond_user_fail() {
    String res_str = JSONParse.json_request("signup", "server", "", "", "", "", "fail");
    out.print(res_str);
    return true;
  }

  private Boolean check_user_exist(String uemail) {
    ResultSet rs = DataStart.q_userinfo_check(uemail);
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

  private boolean is_dynamic_web() {
    if (this.from.equals("pdm web")) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Gets the user name of the packet
   */
  private String read_stream(InputStream stream) throws IOException {
    String _str = "";
    int i;
    while ((i = stream.read()) != -1) {
      _str = _str + (char) i;
    }
    return _str;
  }
}