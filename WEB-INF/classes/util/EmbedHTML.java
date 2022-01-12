package util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EmbedHTML {

  final private static String dir = "/usr/local/tomcat/webapps";

  public static String plain( String cler, String a){
    return String.format(read_into_string("/resc/check_email.html", cler), a);
    // String.format(read_into_string("/resc/check_email.html"), "请点击邮箱 "+umail+" 中的链接来完成注册。")
  }
  public static String email(String cler, String a,String b,String c){
    return String.format(read_into_string("/resc/email_link.html", cler), a, b,c);
  }

  private static String read_into_string(String f_name, String cler){
      String f_text = "";
      String bad_dir = dir+cler;
      try{
          System.out.println("[embed html] Reading file : "+bad_dir+f_name);
          File f = new File(bad_dir+f_name);
          Path fp = Path.of(bad_dir+f_name);
          f_text= Files.readString(fp);
          f_text=f_text.replaceAll("\n","");
          // Scanner s = new Scanner(f);
          // while(s.hasNextLine()){
          //     f_text=f_text+s.next()+" ";
          // }
      }
      catch (Exception e){
          System.out.println("[embed html] Reading file failure: "+bad_dir);
      }
      System.out.println("[embed html] reading: "+f_text);
      return f_text;
  }
}


