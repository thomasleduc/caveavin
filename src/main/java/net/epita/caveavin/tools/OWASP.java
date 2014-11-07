package net.epita.caveavin.tools;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * OWASP reference found here : https://www.owasp.org/index.php/Hashing_Java
 */
public class OWASP {

    public static byte[] getHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        return digest.digest(password.getBytes("UTF-8"));
    }

    public static String byteToBase64(byte[] data){
        BASE64Encoder endecoder = new BASE64Encoder();
        return endecoder.encode(data);
    }
}
