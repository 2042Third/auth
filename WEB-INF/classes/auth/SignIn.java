package auth;

import util.*;
import storage.*;
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class SignIn extends HttpServlet {
  private String auser="";
  private String aemail="";
  private String acreation="";

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession(true);

    // PDM part
    // read from template


    Date date = new Date();

    // String uname = request.getParameter("uname");
    String upw = request.getParameter("upw");
    String umail = request.getParameter("umail");

    if(upw.equals("") || umail.equals("") ||upw.equals("test") || umail.equals("test")){
      System.out.println("[Auth] "+date.getTime()+"");
      System.out.println("[Auth] User sign in failue: no email or password.");
      System.out.println("[Auth] User sign in failue, session id: "+request.getSession());
      System.out.println("");
    }

    check_user_login(upw, umail);

    System.out.println("[Auth Register] "+date.getTime()+"");
    System.out.printf("[Auth Register] User sign in: email \"%s\", creation date \"%s\", user name : \"%s\"\n\n", aemail, acreation, auser);

  }

  public Boolean check_user_login(String uemail,String upass){
        ResultSet rs = DataStart.q_userinfo_reg(uemail, upass);
        Boolean rt = false;
        try{
            while (rs.next()){
                rt = true;
                auser = rs.getString("name");
                aemail = rs.getString("email");
                acreation = rs.getString("creation");
            }
        }
        catch (Exception e){
            System.out.println("[Authentication] SQL no result in query or failure happened ");
        }
        return rt;
    }

}