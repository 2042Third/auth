/**
 * Yi Yang
 * for pdm notes
 * 05/20/2022
 * */
package auth;

import java.io.*;
import jakarta.servlet.*;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Notes sync and update class
 * 
 * @author Yi Yang
 */
public class Notes extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String tester = request.getRequestURL().toString();
    // response.setParameter("en");
    // response.setParameter("id");
    String data;
    NotesUser usr_ = new NotesUser();
    response.setContentType("text/html"); // response stream
    try {
      usr_.set_out(response.getWriter());
      data = read_stream(request.getInputStream()); // input stream
    } catch (IOException e) {
      System.out.printf("[Note] error, cannot read incoming message or get the return stream\n");
      return;
    }
    System.out.printf("[Note] new request:\"%s\"\n", data);
    usr_.parse_json(data);
    usr_.enable_debug(); // DEBUG ONLY, REMOVE WHEN PRODUCTION
    usr_.resolve_action();

  }

  /**
   * Notifies user of success creation of a note, and the note's assigned id.
   * 
   */
  /**
   * Gets the content of the packet
   * 
   * @param stream input of the user
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