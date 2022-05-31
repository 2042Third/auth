package auth;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Map;
import util.*;

import storage.DataStart;
import util.JSONParse;

public class NotesUser extends User {

  private Map<String, Object> json_data;
  private note requests = new note();
  // private PrintWriter out;

  /**
   * Parses the incoming json data
   * 
   * @param data json string
   */
  public void parse_json(String data) {
    json_data = JSONParse.parse(data);
    requests.content = (String) json_data.get("content");
    requests.note_id = (String) json_data.get("note_id");
    requests.email = (String) json_data.get("email");
    requests.sess = (String) json_data.get("sess");
    requests.ntype = (String) json_data.get("ntype");
    requests.username = (String) json_data.get("username");
    requests.unencrypted_hash = (String) json_data.get("h");
  }

  /**
   * Resolves the next action of the request, and perform the actions.
   * Update or new.
   * 
   */
  public void resolve_action() {
    System.out.printf("[Note User] request resolving user=%s\n", requests.username);
    // New Note
    if (requests.ntype.equals("1")) {
      System.out.printf("[Note User] request new note from user=%s\n", requests.username);
      ResultSet rs = DataStart.u_notes_new(requests);
      Boolean rt = false;
      try {
        while (rs.next()) {
          requests.note_id = rs.getString("noteid");
          rt = true;
          System.out.printf("[Note User] query result: note_id=%s\n", requests.note_id);
        }
      } catch (Exception e) {
        System.out.println("[Note User] SQL no result in query or failure happened ");
      }
      respond_user_note();
    }

    // Update Note
    else if (requests.ntype.equals("0")) {
      DataStart.u_notes_update(requests);
    }

    // Get Heads
    else if (requests.ntype.equals("2")) {

    }
  }

  /**
   * Respondes the user of the note request
   * 
   */
  protected Boolean respond_user_note() {
    String res_str = JSONParse.note_request(requests);
    out.print(res_str);
    return true;
  }

  /**
   * Check the session key and get the heads of all notes for this user.
   */
  private Boolean get_notes_heads() {
    ResultSet rs = DataStart.q_notes_heads(requests);
    Boolean rt = false;
    try {
      while (rs.next()) {
        rt = true;
        System.out.printf("[Note User] query result: user exists\n");
      }
    } catch (Exception e) {
      System.out.println("[Note User] SQL no result in query or failure happened ");
    }
    return rt;
  }

}
