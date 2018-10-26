package com.lvshou.magic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import com.lvshou.magic.statics.WeChatConfig;

public class HttpUtils {

	 public static String httpsRequest(String requestUrl, String requestMethod,String strlen) {
	        try {
	        	KeyStore keyStore=KeyStore.getInstance("PKCS12");
	        	 FileInputStream instream = new FileInputStream(new File(WeChatConfig.CERT_PATH));//P12文件目录  
	             try {  
	                 /** 
	                  * 此处要改 
	                  * */  
	                 keyStore.load(instream, WeChatConfig.MCH_ID.toCharArray());//这里写密码..默认是你的MCHID  
	             } finally {  
	                 instream.close();  
	             }  
	            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	            TrustManager[] tm = { new MyX509TrustManager() };
	            //SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	            SSLContext sslContext=SSLContexts.custom().loadKeyMaterial(keyStore, WeChatConfig.MCH_ID.toCharArray()).build();
	            //sslContext.init(null, tm, new java.security.SecureRandom());
	            // 从上述SSLContext对象中得到SSLSocketFactory对象
	            SSLSocketFactory ssf = sslContext.getSocketFactory();
	            URL url = new URL(requestUrl);
	            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	            conn.setSSLSocketFactory(ssf);
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            // 设置请求方式（GET/POST）
	            conn.setRequestMethod(requestMethod);
	            if(!requestMethod.equalsIgnoreCase("get")) 
	            	conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
	     
	            if(strlen!=null) {
	            	OutputStream outputStream = conn.getOutputStream();
	            	System.out.println("strlen="+strlen);
	            	outputStream.write(strlen.getBytes("UTF-8"));
	            	outputStream.flush();
	            	outputStream.close();
	            }
	            // 从输入流读取返回内容
	            InputStream inputStream = conn.getInputStream();
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String str = null;
	            StringBuffer buffer = new StringBuffer();
	            while ((str = bufferedReader.readLine()) != null) {
	                buffer.append(str);
	            }
	            // 释放资源
	            bufferedReader.close();
	            inputStreamReader.close();
	            inputStream.close();
	            inputStream = null;
	            conn.disconnect();
	            System.out.println("发送连接成功"+buffer.toString());
	            /*
	            Document doc=DocumentHelper.parseText(buffer.toString());
	            Map map=XMLUtil.Dom2Map(doc);
	            map.forEach((k,v)->{System.out.println(k+"------"+v);});
	            paySort(map.get("prepay_id"));*/
	            return buffer.toString();
	        } catch (ConnectException ce) {
	          System.out.println("连接超时："+ce+"");
	        } catch (Exception e) {
	           System.out.println("https请求异常：{"+e+"}");
	        }
	        
	        return null;
	    }
}
