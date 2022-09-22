package auth.signup;

import java.io.*;
import jakarta.servlet.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUp extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    SignUpUser usr = new SignUpUser();
    usr.set_out(response.getWriter());
    System.out.println("Data read from the file: ");
    // Convert byte array into string
    String data = read_stream(request.getInputStream());
    System.out.println(data);
    usr.parse_json(data);
    System.out.println("parse success");
    final ServletContext servletContext = getServletContext();
    usr.set_bad_dir(servletContext.getRealPath(servletContext.getContextPath()));
    System.out.println("bad dir success");
    usr.resolve_singup();
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