package auth;

import util.*;
import storage.*;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Authenticate extends HttpServlet {
    private String auser = "";
    private String aemail = "";
    private String acreation = "";

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String auth_str = request.getPathInfo().split("/")[1];

        System.out.println("[Authentication] user Auth \"" + auth_str + "\"");
        if (check_reg_key(auth_str)) {
            Long ctime = Long.parseLong(acreation);
            Date cdate = new java.util.Date(ctime * 1000L);
            System.out.println("[Authentication] New User registration sucess for \"" + auser + "\" email \"" + aemail
                    + "\" created: " + cdate.toString());

            DataStart.u_userinfo_reg(aemail);
            out.println(EmbedHTML.plain("/auth", "Registration Successful!"));
        } else {
            System.out.println("[Authentication] New user Auth unknown received ");
            out.println(EmbedHTML.plain("/auth", "Registration Unsuccessful"));

        }
        // check if the auth string matches anything
    }

    public Boolean check_reg_key(String reg_key) {
        ResultSet rs = DataStart.q_userinfo_reg(reg_key);
        Boolean rt = false;
        try {
            while (rs.next()) {
                rt = true;
                auser = rs.getString("name");
                aemail = rs.getString("email");
                acreation = rs.getString("creation");
            }
        } catch (Exception e) {
            System.out.println("[Authentication] SQL no result in query or failure happened ");
        }
        return rt;
    }

}