package util;

import java.nio.file.Files;
import java.nio.file.Path;

public class EmbedHTML {

  final private static String dir = "/usr/local/tomcat/webapps";

  public static String plain(String cler, String a) {
    return String.format(read_into_string("/resc/check_email.html", cler), a);
  }

  public static String email(String cler, String a, String b, String c) {
    return String.format(read_into_string("/resc/email_link.html", cler), a, b, c);
  }

  private static String read_into_string(String f_name, String cler) {
    String f_text = "";
    String bad_dir = dir + cler;
    try {
      System.out.println("[embed html] Reading file : " + bad_dir + f_name);
      Path fp = Path.of(bad_dir + f_name);
      f_text = Files.readString(fp);
      f_text = f_text.replaceAll("\n", "");
    } catch (Exception e) {
      System.out.println("[embed html] Reading file failure: " + bad_dir);
    }
    System.out.println("[embed html] reading: " + f_text);
    return f_text;
  }
}
