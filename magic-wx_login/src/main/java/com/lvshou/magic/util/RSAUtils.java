package com.lvshou.magic.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import com.lvshou.magic.statics.WeChatConfig;
import com.lvshou.magic.utils.MainUUID;

public class RSAUtils {

	public static String connect() throws UnsupportedEncodingException {
		SortedMap<String, String> map=new TreeMap<>();
		map.put("mch_id", WeChatConfig.MCH_ID);
		map.put("nonce_str", MainUUID.getUUID());
		map.put("sign_type", WeChatConfig.SIGNTYPE);
		map.put("sign",MD5Util.encode(ParseUtil.parseFirst(map), "utf-8"));
		return HttpUtils.httpsRequest(WeChatConfig.RSA_URL, "POST", XMLUtil.parseToString(map));
	}


	public static byte[] encrypt(byte[] plainBytes, String public_key, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {  
		byte[] buffer = Base64.decodeBase64(public_key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		int keyByteSize = keyLength / 8;  
	    int encryptBlockSize = keyByteSize - reserveSize;  
	    int nBlock = plainBytes.length / encryptBlockSize;  
	    if ((plainBytes.length % encryptBlockSize) != 0) {  
	        nBlock += 1;  
	    }  
	    ByteArrayOutputStream outbuf = null;  
	    try {  
	        Cipher cipher = Cipher.getInstance(cipherAlgorithm);  
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
	
	        outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);  
	        for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {  
	            int inputLen = plainBytes.length - offset;  
	            if (inputLen > encryptBlockSize) {  
	                inputLen = encryptBlockSize;  
	            }  
	            byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);  
	            outbuf.write(encryptedBlock);  
	        }  
	        outbuf.flush();  
	        return outbuf.toByteArray();  
	    } catch (Exception e) {  
	        throw new Exception("ENCRYPT ERROR:", e);  
	    } finally {  
	        try{  
	            if(outbuf != null){  
	                outbuf.close();  
	            }  
	        }catch (Exception e){  
	            outbuf = null;  
	            throw new Exception("CLOSE ByteArrayOutputStream ERROR:", e);  
	        }  
	    }  
	}  

}
