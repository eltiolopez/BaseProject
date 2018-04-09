package com.jld.base.core.utils;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public class AESencrypter {
    
	private static final String ALGO = "AES";
	private static final byte[] keyValue = 
			new byte[] { 'Z', '1', 'A', '5', 'e', '0', 'r', 'S', 'o', 'p', 'A','5', 'x', '2', 'P', 'F' };


	@SuppressWarnings("restriction")
	public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    @SuppressWarnings("restriction")
	public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    
    private static Key generateKey() throws Exception {
    	Key key = new SecretKeySpec(keyValue, ALGO);
    	return key;
    }

}


