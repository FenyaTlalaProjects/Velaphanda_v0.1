package com.demo.dao.impl;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class PasswordEncrypt {

	 static String retPassword = "";
	public static String encryptPassword(String password){
		try{
			String key = "Bar12345Bar12345"; // 128 bit key
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        // encrypt the text
	        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	        byte[] encrypted = cipher.doFinal(password.getBytes());
			retPassword = new String(encrypted);
		}catch(Exception e){
			retPassword = e.getMessage();
		}
		return retPassword;
	}
	public static String decryptPassword(String password){
		try{
			
            String key = "Bar12345Bar12345"; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
           
            //decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            
            
		}catch(Exception e){
			retPassword = e.getMessage();
		}
		return retPassword;
	}
}
