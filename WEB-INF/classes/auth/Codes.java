package auth;

import java.util.Date;

import util.SHA3;

public class Codes {
  /**
   * Returns a randomly generated 128-bit hash
   * 
   * @return randomly generated hash
   */
  public static String byte_rand128() throws IllegalArgumentException {
    Date date = new Date();
    String intermediate = "" + Math.random();
    intermediate += date.getTime() + "";
    try {
      return SHA3.get_sha3B(intermediate);
    } catch (Exception e) {
      System.out.println("[Codes generator fail] hash function failed.");
      return "";
    }
  }

  /**
   * Returns a randomly generated 128-bit hash, with additional info
   * 
   * @param info additional information that can be used to further target the
   *             generated code
   * @return randomly generated hash
   */
  public static String byte_rand128(String info) throws IllegalArgumentException {
    Date date = new Date();
    String intermediate = "" + Math.random();
    intermediate += date.getTime() + "";
    intermediate += info;
    try {
      return SHA3.get_sha3B(intermediate);
    } catch (Exception e) {
      System.out.println("[Codes generator fail] hash function failed.");
      return "";
    }
  }

  /**
   * Returns a randomly generated 512-bit hash, with 2 additional info
   * 
   * @param info additional information that can be used to further target the
   *             generated code
   * @return randomly generated hash
   */
  public static String byte_rand512(String info1, String info2) throws IllegalArgumentException {
    Date date = new Date();
    String intermediate = "" + Math.random();
    intermediate += date.getTime() + "";
    intermediate += info1;
    intermediate += info2;
    try {
      return SHA3.get_sha3A(intermediate);
    } catch (Exception e) {
      System.out.println("[Codes generator fail] hash function failed.");
      return "";
    }
  }
}
