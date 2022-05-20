/**
 * Yi Yang
 * for pdm notes
 * 05/20/2022
 * */
package auth;

import util.*;
import storage.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.util.Date;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
/**
 * Notes sync and update class
 * @author Yi Yang
 * */
public class Notes extends HttpServlet {
  private  PrintWriter out;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    response.setContentType("text/html"); // response stream
    out = response.getWriter();  
    
    String data = read_stream(request.getInputStream()); // input stream

  }

  /**
   * Gets the user name of the packet
   * @param stream input of the user
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