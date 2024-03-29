package storage;

/**
 * Queries for pdm
 * 
 * @author Yi Yang
 */
public class Queries {
        public static String q_userinfo_reg = "select name, email, creation from userinfo where register_key = ?";
        public static String q_userinfo_login = "SELECT "
          + "u.name AS name, u.email AS email, u.creation AS creation, u.registered AS registered "
          + "FROM userinfo u "
          + "WHERE "
          // + "s.userid = u.id "
          + "u.email = ? "
          + "AND u.spw = ? "
          + ";";
        public static String u_userinfo_sess = "insert into sessions (userid, key, sessip ) "
                + "select u.id , ?, ?  from "
                + "userinfo u "
                + " where u.email = ? "
                + " ;";
        public static String u_userinfo_sess_delete_existing = "delete from sessions "
                + " where userid in "
                + " ( "
                + " select id from userinfo u "
                + "   where u.email = ? "
                + " ) "
                + " and sessip = ?"
                + " ;";
        public static String u_userinfo_reg = "update userinfo set registered = ? where email = ?;";
        public static String q_userinfo_check = "select 1 from userinfo where email = lower(?);";
        public static String u_userinfo_chpw = "update userinfo set spw = ? where email = ?;";
        public static String u_userinfo_chpw_code = "insert into changepassword (userid, refcode) "
                + "select u.id , ?  from "
                + "userinfo u "
                + " where u.email = ? "
                + " returning noteid;";
        public static String register_user = "INSERT INTO userinfo(name, spw, creation, product, email, register_key, logs) "
                + "VALUES(?, ?, ?, ?, lower(?), ?, ?) "
                + "ON CONFLICT (email) DO UPDATE SET register_key = ?;";
        public static String u_notes_new = "insert into notes(userid, content, h, intgrh) "
                + "select u.id , ? , ?, ? from "
                + "userinfo u, sessions s "
                + " where u.email = ? "
                + " and u.id = s.userid "
                + " and s.key = ? "
                + " returning noteid;";
        public static String u_notes_update = "update notes "
                + " set content = ? "
                + ", h = ? "
                + ", intgrh = ? "
                + ", heading = ?"
                + ", update_time = CURRENT_TIMESTAMP "
                + " from sessions s "
                + " where noteid = ? "
                + " and  s.userid = notes.userid "
                + " and s.key = ?"
                + " returning EXTRACT(EPOCH FROM notes.update_time) update_time"
                + ", EXTRACT(EPOCH FROM notes.time) AS time;";
        public static String u_notes_delete = "update notes "
                + " set deleted = ? "
                + ", update_time = CURRENT_TIMESTAMP "
                + " from sessions s "
                + " where noteid = ? "
                + " and s.userid = notes.userid "
                + " and s.key = ?;";
        public static String q_notes_heads = "select n.heading AS head, EXTRACT(EPOCH FROM n.time) AS time, EXTRACT(EPOCH FROM n.update_time) AS update_time, n.h AS h, n.noteid AS noteid"
                + " from userinfo u, notes n, sessions s"
                + " where u.email = ?"
                + " and s.key = ?"
                + " and u.id = s.userid"
                + " and u.id = n.userid"
                + " and n.deleted = 0"
                + " group by noteid;";
        public static String q_notes_get = "select  n.content AS content, n.heading AS head, EXTRACT(EPOCH FROM n.time) AS time, EXTRACT(EPOCH FROM n.update_time) AS update_time, n.h AS h, n.noteid AS noteid"
                + " from userinfo u, notes n, sessions s"
                + " where u.email = ?"
                + " and s.key = ?"
                + " and n.noteid = ?"
                + " and u.id = s.userid "
                +"and u.id = n.userid group by noteid;";
        // String registerquery = "INSERT INTO userinfo(name, spw, creation, product,
        // email, register_key, logs) VALUES(?, ?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO
        // UPDATE SET txt = EXCLUDED.txt;";
}