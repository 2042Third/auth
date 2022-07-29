package security;

public class EncryptionTools {
    static {
        System.loadLibrary("cc20"); // loads the encryption module
    }
    public native void pp_hash_wrap(); // the function in c
    // Test Driver
    public static void main(String[] args) {
        System.out.println("Doing");
//        new EncryptionTools().pp_hash_wrap();  // Invoke native method
    }
}
