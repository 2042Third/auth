package storage;

/**
 * Queries for pdm
 * 
 * @author Yi Yang
 */
public class Queries {
        public static String q_userinfo_reg = "select name, email, creation from userinfo where register_key = ?";
        public static String q_userinfo_login = "select name, email, creation, registered from userinfo where email = ? and spw = ?;";
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
                        + "userinfo u "
                        + " where u.email = ? "
                        + " returning noteid;";
        public static String u_notes_update = "update table notes n "
                        + "set n.content = ? "
                        + "n.h = ? "
                        + "n.intgrh = ? "
                        + "n.noteid = ? "
                        + "n.update_time = CURRENT_TIMESTAMP "
                        + " where n.id;";
        public static String q_notes_heads = "select n.heading head, n.time time, n.h h, n.noteid noteid"
                        + " from userinfo u, notes n, sessions s"
                        + " where u.email = ?"
                        + " and s.key = ?"
                        + " and u.id = s.userid and u.id = n.usrid;";
        // String registerquery = "INSERT INTO userinfo(name, spw, creation, product,
        // email, register_key, logs) VALUES(?, ?, ?, ?, ?, ?, ?) ON CONFLICT (id) DO
        // UPDATE SET txt = EXCLUDED.txt;";
}