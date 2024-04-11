package org.fleetopgroup.constant;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;

public class INcreptTest {

	public static void main(String[] args) {

			try{
				String 						sharedKey					= "A1234&ABCDE/98745#000078";
		    	String 						sharedVector				= "8,7,5,6,4,1,2,3,18,17,15,16,14,11,12,13";
		    	
		    	String[] byteStrings = sharedVector.split(",");
		    
		    	byte[] byteData = new byte[byteStrings.length];
				for (int i = 0; i < byteData.length; i++){
				    byteData[i] = Byte.parseByte(byteStrings[i]); 
				}
				
				String jsonStr;
	    		JSONObject json = new JSONObject();
	    		json.put("FROM_DT", "2019-05-01");
	    		json.put("TO_DT", "2019-05-03");
	    		json.put("MOBILENUMBER", "9945788288");
	    		json.put("VEHICLENUMBER", "PY01CK8217");
	    		json.put("PARAM1", "");
	    		json.put("PARAM2", "");
	    		json.put("PARAM3", "");
	    		json.put("PARAM4", "");
	    		json.put("PARAM5", "");
	    		
	    		
	    		jsonStr =  json.toString();
	    		
				
		    	
					Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
					c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sharedKey.getBytes(), "AES"), new IvParameterSpec(byteData));
					byte[] encrypted = c.doFinal(jsonStr.getBytes("UTF-8"));
					String strret = Base64.getEncoder().encodeToString(encrypted);
					strret = strret.replace("\n", "");
					
					System.err.println("dadfsfsf"+ URLEncoder.encode(strret,"UTF-8"));
					
					
					
					String str= "WIRQeiapT6eFGORYdP3%2b8ix8Ff%2fESOrFvxDuLYxgr1vPBD2NCFK3ckU2PXfM24Z3O1ubva%2f5k5MEJcauh2GLOA%3d%3d";
					Cipher c32 = Cipher.getInstance("AES/CBC/PKCS5Padding");
					c32.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sharedKey.getBytes(), "AES"), new IvParameterSpec(byteData));
					byte[] decrypted = c32.doFinal(Base64.getDecoder().decode(URLDecoder.decode(str,"UTF-8")));
					
					System.err.println("decrypted"+  new String(decrypted, "UTF-8"));
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}

}
