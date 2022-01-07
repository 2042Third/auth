package auth;

import util.*;
import storage.*;
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class SignUp extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession(true);

    // PDM part
    // read from template


    Date date = new Date();

    String uname = request.getParameter("uname");
    String upw = request.getParameter("upw");
    String umail = request.getParameter("umail");

    System.out.printf("[Auth Register] User register: name \"%s\", email \"%s\", password \"%s\"\n", uname, umail, upw);

    SHA3 s3 = new SHA3();
    String intermediate = ""+Math.random();
    intermediate += date.getTime()+"";
    intermediate += uname;
    intermediate += umail;
    String reg_key = "";
    try{
      reg_key = s3.get_sha3A(intermediate);
    }
    catch (Exception e){
      System.out.println("[Auth Register] hash function for registration key failed.");
      return;
    }

    DataStart.register_user(uname, umail, upw, "pdm static web", reg_key);


    // print session contents

    // Enumeration e = session.getAttributeNames();
    // while (e.hasMoreElements()) {
    //   String name = (String) e.nextElement();
    //   String value = session.getAttribute(name).toString();
    //   // out.println(name + " = " + value);
    // }
  }
}