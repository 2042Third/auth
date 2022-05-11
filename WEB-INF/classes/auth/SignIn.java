package auth;

import util.*;
import storage.*;
import java.util.prefs.*;
import java.util.Map;
import java.net.URLDecoder;
import java.sql.*;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import jakarta.servlet.ServletException;
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
  private String areg_status="";
  private String asession="session-placeholder";
  private  PrintWriter out;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    Map<String,Object> json_data;
    byte[] array = new byte[1024];
    JSONParse parser=new JSONParse();

    response.setContentType("text/html");
    out = response.getWriter();
    // PDM part

    Date date = new Date();

    System.out.println("Data read from the file: ");

    // Convert byte array into string
    String data = read_stream(request.getInputStream());
    System.out.println(data);

    json_data = parser.parse(data);

    String upw = (String)json_data.get("upw");
    String umail = (String)json_data.get("umail");
    System.out.println("[Auth ] "+date.getTime()+"");
    System.out.printf("[Auth ] User try sign in: email \"%s\"\n\n", umail);

    if(upw.equals("") || umail.equals("") ||upw.equals("test") || umail.equals("test")){
      System.out.println("[Auth] "+date.getTime()+"");
      System.out.println("[Auth] User sign in failue: no email or password.");
      System.out.println("");
    }

    if(check_user_login(umail,upw )){
      System.out.println("[Auth ] "+date.getTime()+"");
      if(areg_status.equals("1")){
        System.out.printf("[Auth ] Registered user \"%s\" sign in\n", auser);
        respond_user();
      }
      else{
        System.out.printf("[Auth ] Unregistered user \"%s\" sign in\n", auser);
      }
      System.out.printf("[Auth ] User sign in: email \"%s\", creation date \"%s\", user name : \"%s\"\n\n", aemail, acreation, auser);
    }
    else{
      System.out.printf("[Auth ] Failure User sign in: email \"%s\", pswd \"%s\"\n\n",umail,upw);
      respond_user_fail();
    }

    

  }

  private Boolean respond_user(){
    JSONParse res = new JSONParse();
    String res_str = res.json_request("login", "server", auser, acreation,aemail,asession,"Login successful!");
    out.print(res_str);
    return true;
  }

  private Boolean respond_user_fail(){
    JSONParse res = new JSONParse();
    String res_str = res.json_request("login", "server", "","","","","Login failed!");
    out.print(res_str);
    return true;
  }

  private Boolean check_user_login(String uemail,String upass){
        ResultSet rs = DataStart.q_userinfo_login(uemail, upass);
        Boolean rt = false;
        try{
            while (rs.next()){
                rt = true;
                auser = rs.getString("name");
                aemail = rs.getString("email");
                acreation = rs.getString("creation");
                areg_status = rs.getString("registered");
                System.out.printf("[Authentication] query result: %s, %s, %s, %s\n",auser,aemail,acreation,areg_status);

            }
        }
        catch (Exception e){
            System.out.println("[Authentication] SQL no result in query or failure happened ");
        }
        return rt;
    }
    /**
     * Gets the user name of the packet
     * */
    private String read_stream(InputStream stream)throws IOException{
        String _str = "";
        int i;
        while((i = stream.read())!=-1) {
            _str = _str+(char)i;
        }
        return _str;
    }
}