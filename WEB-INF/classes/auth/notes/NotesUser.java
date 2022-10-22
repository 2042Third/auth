package auth.notes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import auth.Action;
import auth.User;
import util.*;

import storage.DataStart;
import util.JSONParse;

public class NotesUser extends User {

  private Map<String, Object> json_data;
  private note requests = new note();
  private Action action = new NotesActionsNew();
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
    requests.head = (String) json_data.get("head");
    requests.username = (String) json_data.get("username");
    requests.unencrypted_hash = (String) json_data.get("h");
  }

  /**
   * Sets the object for debug. Insecure.
   * 
   */
  public void enable_debug() {
    requests.sess = "debugkey";
  }

  /**
   * Resolves the next action of the request, and perform the actions.
   * Update or new.
   * 
   */
  public void resolve_action() {
    requests.status = "success";
    System.out.printf("[Note User] request resolving user=%s\n", requests.username);
    // New Note
    if (requests.ntype.equals("new")) {
      action = new NotesActionsNew();
      action.execute();
    }

    // Update Note
    else if (requests.ntype.equals("update")) {
      action = new NotesActionUpdate();
      action.execute();
    }

    // Get Heads, not respond function, intigrated instead
    else if (requests.ntype.equals("heads")) {
      action = new NotesActionHeads();
      action.execute();
    }
    // Get a note
    else if (requests.ntype.equals("retrieve")) {
      action = new NotesActionRetrieve();
      action.execute();
    }
    // Delete anote
    else if (requests.ntype.equals("delete")) {
      action = new NotesActionDelete();
      action.execute();
    }
  }
  /**
   * Reads and updates the request note object
   *
   */
  protected void process_note_update() throws SQLException {

    note update_stat = extract_time_return(DataStart.u_notes_update(requests));
    requests.update_time = update_stat.update_time;
    requests.time = update_stat.time;
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
  private Object[] get_notes_heads() {
    ResultSet rs = DataStart.q_notes_heads(requests);
    ArrayList<note_head> all_heads = new ArrayList<note_head>();
    note_head nh;
    try {
      while (rs.next()) {
        nh = new note_head();
        nh.note_id = rs.getString("noteid");
        nh.head = rs.getString("head");
        nh.time = rs.getString("time");
        nh.update_time = rs.getString("update_time");
        all_heads.add(nh);
        System.out.printf("[Note User] query heads result: note_id's=%s\n",
            nh.note_id);
      }
    } catch (Exception e) {
      System.out.println("[Note User] SQL no result in query or failure happened ");
      requests.status = "fail";
      return null;
    }
    return all_heads.toArray();
  }

  /**
   * gets the encrypted content of the note
   * ==> n.content content, n.heading head, n.time time, n.h h, n.noteid noteid
   */
  private void get_note() {
    ResultSet rs = DataStart.q_notes_get(requests);
    try {
      while (rs.next()) {
        requests.content = rs.getString("content");
        requests.head = rs.getString("head");
        requests.time = rs.getString("time");
        requests.update_time = rs.getString("update_time");
        requests.unencrypted_hash = rs.getString("h");
        requests.note_id = rs.getString("noteid");
        System.out.printf("[Note User] query retrieve result: note_id's=%s\n",
            requests.note_id);
      }
    } catch (Exception e) {
      System.out.println("[Note User] SQL no result in query or failure happened ");
      requests.status = "fail";
    }
  }
  private note extract_time_return (ResultSet rs){
    note rt = new note();
    try {
      while (rs.next()) {
        rt.update_time = rs.getString("update_time");
        rt.time = rs.getString("time");

      }
      return rt;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("[Note User] SQL no result in query or failure happened ");
      rt.status = "fail";
      return rt;
    }
  }
  private note parse_note_return (ResultSet rs){
    note rt = new note();
    try {
      while (rs.next()) {
        rt.content = rs.getString("content");
        rt.head = rs.getString("head");
        rt.time = rs.getString("time");
        rt.update_time = rs.getString("update_time");
        rt.unencrypted_hash = rs.getString("h");
        rt.note_id = rs.getString("noteid");
        System.out.printf("[Note User] query retrieve result: note_id's=%s\n",
                rt.note_id);
      }
      return rt;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("[Note User] SQL no result in query or failure happened ");
      rt.status = "fail";
      return rt;
    }
  }

  class NotesActionsNew implements Action {
    @Override
    public void execute() {
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
      requests.ntype = "new_return";
      respond_user_note();
    }
  }

  class NotesActionUpdate implements Action {
    @Override
    public void execute() {
      try {
        process_note_update();
      } catch (SQLException e) {
        requests.status = "fail";
        System.out.println("[Note User] Update failure, no action performed.");
        e.printStackTrace();
      }
      String res_str = JSONParse.note_request(requests);
      out.print(res_str);
      System.out.printf("[Note User] request complete update user=%s\n ", requests.email);
    }
  }

  class NotesActionHeads implements Action {
    @Override
    public void execute() {
      System.out.printf("[Note User] request heads from user=%s\n", requests.username);
      Object[] nhs = get_notes_heads();
      requests.ntype = "heads_return";
      requests.note_id = "listed";
      requests.unencrypted_hash = "listed";
      String res_str = JSONParse.note_head_request(requests, nhs);
      out.print(res_str);
      System.out.printf("[Note User] request complete heads from user=%s\n ", requests.email);
    }
  }

  class NotesActionRetrieve implements Action {
    @Override
    public void execute() {
      System.out.printf("[Note User] request retrieve from user=%s\n", requests.username);
      requests.ntype = "retrieve_return";
      get_note();
      String res_str = JSONParse.note_request(requests);
      out.print(res_str);
      System.out.printf("[Note User] request complete retrieve from user=%s\n ", requests.email);
    }
  }

  class NotesActionDelete implements Action {
    @Override
    public void execute(){
      System.out.printf("[Note User] request delete note=%s\n", requests.username);
      try {
        DataStart.u_notes_delete(requests);
      } catch (SQLException e) {
        requests.status = "fail";
        System.out.println("[Note User] Update failure, no action performed.");
        e.printStackTrace();
      }
      String res_str = JSONParse.note_request(requests);
      out.print(res_str);
      System.out.printf("[Note User] delete request complete update user=%s\n ", requests.email);
    }
  }

}
