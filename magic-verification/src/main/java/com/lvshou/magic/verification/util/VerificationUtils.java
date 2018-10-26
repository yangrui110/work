package com.lvshou.magic.verification.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class VerificationUtils {


	 public static String httpsRequest(String requestUrl, String requestMethod,String strlen) {
		 try {
	            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	            TrustManager[] tm = { new MyX509TrustManager() };
	            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	            sslContext.init(null, tm, new java.security.SecureRandom());
	            // 从上述SSLContext对象中得到SSLSocketFactory对象
	            SSLSocketFactory ssf=sslContext.getSocketFactory();
	            URL url = new URL(null,requestUrl,new sun.net.www.protocol.https.Handler());
	            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	            conn.setSSLSocketFactory(ssf);
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            // 设置请求方式（GET/POST）
	            conn.setRequestMethod(requestMethod);
	            conn.setRequestProperty("content-type", "application/text");
	            // 当outputStr不为null时向输出流写数据
	            if (null != strlen) {
	                OutputStream outputStream = conn.getOutputStream();
	                // 注意编码格式
	                outputStream.write(strlen.getBytes("UTF-8"));
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
