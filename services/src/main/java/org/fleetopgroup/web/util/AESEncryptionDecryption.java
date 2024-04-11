package org.fleetopgroup.web.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptionDecryption {
	
	public static String encryptText(String actualText, byte[] sharedkey, byte[] sharedvector) throws Exception{
		try{
			
				Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
				c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sharedkey, "AES"), new IvParameterSpec(sharedvector));
				byte[] encrypted = c.doFinal(actualText.getBytes("UTF-8"));
				String strret = Base64.getEncoder().encodeToString(encrypted);
				strret = strret.replace("\n", "");
				
				return URLEncoder.encode(strret,"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	
	
	public static String decryptText(String encryptedTxt, byte[] sharedkey, byte[] sharedvector) throws Exception{
		try{
				String str= encryptedTxt;
				Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
				c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sharedkey, "AES"), new IvParameterSpec(sharedvector));
				byte[] decrypted = c.doFinal(Base64.getDecoder().decode(URLDecoder.decode(str,"UTF-8")));
				
				return new String(decrypted, "UTF-8");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

}
