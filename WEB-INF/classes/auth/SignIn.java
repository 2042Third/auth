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

    System.out.println("[Auth Register] "+date.getTime()+"");
    System.out.printf("[Auth Register] User sign in: email \"%s\", password \"%s\"\n\n", umail, upw);

  }

}