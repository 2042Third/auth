package auth;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    if (requests.ntype.equals("new")) {
      System.out.printf("[Note User] request new note from user=%s\n", requests.username);
      ResultSet rs = DataStart.u_notes_new(requests);
      try {
        while (rs.next()) {
          requests.note_id = rs.getString("noteid");
          System.out.printf("[Note User] query result: note_id=%s\n", requests.note_id);
        }
      } catch (Exception e) {
        System.out.println("[Note User] SQL no result in query or failure happened ");
      }
      respond_user_note();
    }

    // Update Note
    else if (requests.ntype.equals("update")) {
      DataStart.u_notes_update(requests);
    }

    // Get Heads, not respond function, intigrated instead
    else if (requests.ntype.equals("heads")) {
      System.out.printf("[Note User] request heads from user=%s\n", requests.username);
      note_head[] nhs = get_notes_heads();
      requests.ntype = "heads_return";
      String res_str = JSONParse.note_head_request(requests, nhs);
      out.print(res_str);
      System.out.printf("[Note User] request complete heads from user=%s\n \t heads: %s", res_str);
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
  private note_head[] get_notes_heads() {
    ResultSet rs = DataStart.q_notes_heads(requests);
    ArrayList<note_head> all_heads = new ArrayList<note_head>();
    note_head nh;
    try {
      while (rs.next()) {
        nh = new note_head();
        nh.note_id = rs.getString("noteid");
        nh.head = rs.getString("head");
        all_heads.add(nh);
        System.out.printf("[Note User] query heads result: note_id's=%s\n", nh.note_id);
      }
    } catch (Exception e) {
      System.out.println("[Note User] SQL no result in query or failure happened ");
      return null;
    }
    return (note_head[]) all_heads.toArray();
  }

}
