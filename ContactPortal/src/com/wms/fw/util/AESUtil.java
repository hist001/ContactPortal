/****************************************** 
FILENAME : AESUtil.java 
DATE : 2015.06.22
PROGRAMER : mailbest	
COMMENT : ASE 보안적용 Util
******************************************/
package com.wms.fw.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import com.wms.fw.Config;
import com.wms.fw.Configuration;

public class AESUtil{

//AES+HEX Encode
    public static String AesEncode(String text)throws Exception{
 		try{
 			 Config conf = new Configuration();
 			 String key = conf.get("wms.server.provider.aesKey");
	         Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	         Cipher cipher = Cipher.getInstance("AES");
	         cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	         byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
	         
			String  EndStr=byteArrayToHex(encrypted);

    		return EndStr;
      }catch(Exception e) {
         e.printStackTrace();
    		return  "";
      }    		
    }    
    
//AES+HEX Dedode    
    public static String AesDecode(String text)throws Exception{
 		try{
		  Config conf = new Configuration();
		  String key = conf.get("wms.server.provider.aesKey");
	      Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES");
          cipher.init(Cipher.DECRYPT_MODE, aesKey);
          String EndStr = new String(cipher.doFinal(hexToByteArray(text)),"UTF-8");	         
	         
    		return EndStr;
      }catch(Exception e) {
         e.printStackTrace();
    		return  "";
      }    		
    }    

	// hex to byte[]
	public static byte[] hexToByteArray(String hex)throws Exception{
	    if (hex == null || hex.length() == 0 || hex == "") {
	        return null;
	    }
	    //System.out.println("hex==>"+hex);
	    
	    byte[] ba = new byte[hex.length() / 2];
	    for (int i = 0; i < ba.length; i++) {
	        ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return ba;
	}
	
	// byte[] to hex
	public static String byteArrayToHex(byte[] ba)throws Exception{
	    if (ba == null || ba.length == 0) {
	        return null;
	    }
	
	    StringBuffer sb = new StringBuffer(ba.length * 2);
	    String hexNumber;
	    for (int x = 0; x < ba.length; x++) {
	        hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	    }
	    return sb.toString();
	}     
}
