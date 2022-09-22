package auth.signin;

import java.io.InputStream;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignIn extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    SignInUser usr = new SignInUser();
    usr.set_out(response.getWriter());
    System.out.println("Data read from the file: ");
    // Convert byte array into string
    String data = read_stream(request.getInputStream());
    System.out.println(data);
    usr.parse_json(data);
    usr.check_login();
  }

  private String read_stream(InputStream stream) throws IOException {
    String _str = "";
    int i;
    while ((i = stream.read()) != -1) {
      _str = _str + (char) i;
    }
    return _str;
  }
}