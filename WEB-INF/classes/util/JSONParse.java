package util;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.util.*;
import java.io.StringWriter;
import java.io.IOException;

public final class JSONParse {
  /**
   * Parses the input json and returns a java object-map.
   */
  public static Map<String, Object> parse(String a) {
    Object obj = new Object();
    JSONParser parser = new JSONParser();
    try {
      obj = parser.parse(a);
    } catch (ParseException e) {
      System.out.println(e);
      System.out.println("Jason Parse failure!");
      System.out.println(a);
    }
    return (Map<String, Object>) obj;
  }

  /**
   * Deletes any existing obj, and creates a new one.
   */
  // public void create_obj() {
  // jobj = new JSONObject();
  // }

  /**
   * JSON to string
   * Takes a json object returns a json string
   */
  public static String jsts(JSONObject a) {
    StringWriter out = new StringWriter();
    try {
      a.writeJSONString(out);
    } catch (IOException e) {
      System.out.println("Json to string fialure!");
      return "";
    }
    return out.toString();
  }

  /**
   * Returns a json string from the given input.
   * 
   * @param recv   the receiver
   * @param status boolean that turns to string "true"/"false"
   * @param val    value of the ack if it has one
   */
  public static String json_ack(String recv, Boolean status, String val) {
    // create_obj();
    JSONObject jobj = new JSONObject();
    jobj.put("receiver", recv);
    jobj.put("status", new Boolean(status));
    jobj.put("val", val);
    return jsts(jobj);
  }

  /**
   * Returns a json string from the given input.
   * 
   * @param pph    p2p hash
   * @param sender sender
   * @param recv   receiver
   * @param mh     msg hash
   * @param msg    msg
   * @param val    additional data
   */
  public static String json_msg(String pph, String sender, String recv, String mh, String msg, String val) {
    JSONObject jobj = new JSONObject();
    jobj.put("p2phash", pph);
    jobj.put("sender", sender);
    jobj.put("receiver", recv);
    jobj.put("mhash", mh);
    jobj.put("msg", msg);
    jobj.put("val", val);
    return jsts(jobj);
  }

  /**
   * Returns a json string from the given input.
   * 
   * @param sender sender
   * @param recv   receiver
   * @param v1     data
   * @param v2     data
   * @param v3     data
   */
  public static String json_request(String type, String sender, String recv, String v1, String v2, String v3) {
    JSONObject jobj = new JSONObject();
    jobj.put("type", type);
    jobj.put("sender", sender);
    jobj.put("receiver", recv);
    jobj.put("v1", v1);
    jobj.put("v2", v2);
    jobj.put("v3", v3);
    return jsts(jobj);
  }

  /**
   * Returns a json string from the given input.
   * 
   * @param sender sender
   * @param recv   receiver
   */
  public static String json_request(String type, String sender, userinfo u) {
    JSONObject jobj = new JSONObject();
    jobj.put("type", type);
    jobj.put("sender", sender);
    jobj.put("receiver", u.name);
    jobj.put("v1", u.creation_time);
    jobj.put("v2", u.email);
    jobj.put("v3", u.status);
    return jsts(jobj);
  }

  /**
   * Returns a json string from the given input.
   * 
   * @param sender sender
   * @param recv   receiver
   * @param v1     data
   * @param v2     data
   * @param v3     data
   * @param v4     data
   */
  public static String json_request(String type, String sender, String recv, String v1, String v2, String v3,
      String v4) {
    JSONObject jobj = new JSONObject();
    jobj.put("type", type);
    jobj.put("sender", sender);
    jobj.put("receiver", recv);
    jobj.put("v1", v1);
    jobj.put("v2", v2);
    jobj.put("v3", v3);
    jobj.put("v4", v4);
    return jsts(jobj);
  }

  /**
   * Returns a json string from the given input.
   * 
   * @param sender sender
   * @param recv   receiver
   * @param v1     data
   * @param v2     data
   * @param v3     data
   * @param v4     data
   */
  public static String named_json_request(String type, String sender, String recv, String v1, String v2, String v3,
      String v4) {
    JSONObject jobj = new JSONObject();
    jobj.put("type", type);
    jobj.put("sender", sender);
    jobj.put("receiver", recv);
    jobj.put("msg", v1);
    jobj.put("msgh", v2);
    jobj.put("time", v3);
    jobj.put("val", v4);
    return jsts(jobj);
  }

  /**
   * Returns a json string from the given input.
   * 
   * @param rq note request
   */
  public static String note_request(note rq) {
    JSONObject jobj = new JSONObject();
    jobj.put("content", rq.content);
    jobj.put("email", rq.email);
    jobj.put("h", rq.unencrypted_hash);
    jobj.put("note_id", rq.note_id);
    jobj.put("ntype", rq.ntype);
    jobj.put("sess", rq.sess);
    jobj.put("status", rq.status);
    jobj.put("hash", rq.hash);
    return jsts(jobj);
  }
}