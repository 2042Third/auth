package auth;

import java.util.Date;

import util.SHA3;

public class Codes {
  /**
   * Returns a randomly generated 256-bit hash
   * 
   * @return randomly generated hash
   */
  public static String byte_rand256() throws IllegalArgumentException {
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
   * Returns a randomly generated 256-bit hash, with additional info
   * 
   * @param info additional information that can be used to further target the
   *             generated code
   * @return randomly generated hash
   */
  public static String byte_rand256(String info) throws IllegalArgumentException {
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
  /**
   * Returns a randomly generated 512-bit hash, with 1 additional info
   *
   * @param info additional information that can be used to further target the
   *             generated code
   * @return randomly generated hash
   */
  public static String byte_rand512(String info1) throws IllegalArgumentException {
    Date date = new Date();
    String intermediate = "" + Math.random();
    intermediate += date.getTime() + "";
    intermediate += info1;
    try {
      return SHA3.get_sha3A(intermediate);
    } catch (Exception e) {
      System.out.println("[Codes generator fail] hash function failed.");
      return "";
    }
  }

  /**
   * Returns a randomly generated 256-bit hash's first 8 hex characters
   * 
   * @param info additional information that can be used to further target the
   *             generated code
   * @return randomly generated hash
   */
  public static String byte_rand_8(String info) throws IllegalArgumentException {
    return Codes.byte_rand256(info).substring(0, 8);
  }

  /**
   * Generates session key given user password based on random data.
   *
   * @param upw user password
   * @return session key
   * */
  public static String sess_rand_upw (String upw) {
    return byte_rand512(upw);
  }

}
