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
  private PrintWriter out;
  private Map<String, Object> json_data;
  private note requests = new note();

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html"); // response stream
    out = response.getWriter();

    String data = read_stream(request.getInputStream()); // input stream

    json_data = JSONParse.parse(data);
    requests.content = (String) json_data.get("ucontent");
    requests.note_id = (String) json_data.get("unoteid");
    requests.email = (String) json_data.get("uemail");
    requests.is_new_note = (String) json_data.get("is_new_note");
    requests.unencrypted_hash = (String) json_data.get("h");

    if (requests.is_new_note.equals("1")) {
      DataStart.u_notes_new(requests.email, requests.content, requests.unencrypted_hash);
    } else {
      DataStart.u_notes_update(requests.content, requests.unencrypted_hash, requests.note_id);
    }
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

  class note {
    String content;
    String unencrypted_hash;
    String email;
    String note_id;
    String is_new_note;
  }
}