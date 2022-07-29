package security;

public class EncryptionTools {
    static {
        System.loadLibrary("cc20"); // loads the encryption module
    }
    public native void pp_hash_wrap(); // the function in c
}
