package util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Wrapper for sha3 hash.
 * 
 */
public class SHA3 {

  private static final Charset UTF_8 = StandardCharsets.UTF_8;

  /**
   * Takes a string return a sha3-256 hash string.
   */
  public static String get_sha3(String a) throws IllegalArgumentException {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA3-256");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException(e);
    }
    byte[] result = md.digest(a.getBytes(UTF_8));
    return bytesToHex(result);
  }

  /**
   * Takes a string return a sha3-512 hash string.
   */
  public static String get_sha3A(String a) throws IllegalArgumentException {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA3-512");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException(e);
    }
    byte[] result = md.digest(a.getBytes(UTF_8));
    return bytesToHex(result);
  }

  /**
   * Takes a string return a sha3-256 hash string.
   * note*: This is exactly the same as get_SHA3()
   */
  public static String get_sha3B(String a) throws IllegalArgumentException {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA3-256");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException(e);
    }
    byte[] result = md.digest(a.getBytes(UTF_8));
    return bytesToHex(result);
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}
