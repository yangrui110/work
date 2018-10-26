package com.lvshou.magic.message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lvshou.magic.config.PayProperties;
import com.lvshou.magic.statics.WeChatConfig;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.util.HttpUtils;
import com.lvshou.magic.util.XMLUtil;
import com.lvshou.magic.utils.MainUUID;
import com.lvshou.magic.utils.Upload;

@Service
public class MessageImpl implements MessageService {

	@Autowired
	private StringRedisTemplate redis;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PayProperties payProperties;
	
	@Override
	public String response(HttpServletRequest request,String adverPath) {
		// TODO Auto-generated method stub
		try {
		System.out.println("响应点击事件");
		InputStream inputStream=request.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
		System.out.println("输入流中的结果是："+buffer.toString());
		str=buffer.toString();
		inputStream.close();
		inputStreamReader.close();
		bufferedReader.close();
		Document document=DocumentHelper.parseText(str);
		Map map =XMLUtil.Dom2Map(document);
		/*map.keySet().forEach(key->{
			System.out.println("key="+key+"=="+map.get(key));
		});*/
		//获取access_token
		String access=redis.opsForValue().get("basic_access_token");
		if(access==null) {
			access=HttpUtils.httpsRequest(WeChatConfig.ACCESS_TOKEN, "GET", null);
			JSONObject object=new JSONObject(access);
			if(object.get("access_token")==null) return "fail";
			access=(String) object.get("access_token");
			redis.opsForValue().set("basic_access_token", access,7200,TimeUnit.SECONDS);
			System.out.println("token"+access);
		}
		
		String msgType=(String) map.get("MsgType");
		String event=(String) map.get("Event");
		String toUser=(String) map.get("ToUserName");
		String fromUser=(String) map.get("FromUserName");
		String create_time=(String) map.get("CreateTime");
		if(msgType.equalsIgnoreCase("event")) {
			if(event.equalsIgnoreCase("CLICK")) {
				String eventKey=(String) map.get("EventKey");
				//点击分享按钮时，会发送分享图片
				if(eventKey.equalsIgnoreCase("V1001_SHARE")) {
					//生成临时图片
					String userId=createUser(fromUser, access);
					String tempFile=userService.createShareCode(payProperties.getPrefix_path(), userId, adverPath);
					//上传临时素材
					String path=String.format(WeChatConfig.NEW_MEDIA,access,"image");
					String result=Upload.UploadMeida(payProperties.getPrefix_path()+tempFile, path);
					//System.out.println("reult="+result);
					JSONObject jsonObject=new JSONObject(result);
					String media_id=(String) jsonObject.get("media_id");
					String string="<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Image><MediaId><![CDATA[%s]]></MediaId></Image></xml>";
					string=String.format(string, fromUser,toUser,create_time,"image",media_id);
					System.out.println("string="+string);
					return string;
				}
			}
		}else {
			return "";
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
		return "fail";
	}
	public String createUser(String fromUser,String access) throws Exception {
		User u=new User();u.setWid(fromUser);
		User user=userService.findUser(u);
		System.out.println("fromUser="+fromUser);
		if(user==null) {
			//进行用户的插入操作
			String userInfo=String.format(WeChatConfig.USER_INFO, access,fromUser);
			
			userInfo=HttpUtils.httpsRequest(userInfo, "GET", null);
			
			JSONObject jsonObject=new JSONObject(userInfo);
			String userId=MainUUID.getUUID();
			user=new User();
			user.setId(userId);
			user.setWid(fromUser);
			user.setAddress("");
			user.setPhone("");
			user.setName(jsonObject.getString("nickname"));
			user.setProvince(jsonObject.getString("province"));
			user.setCity(jsonObject.getString("city"));
			user.setCountry(jsonObject.getString("country"));
			user.setIcon(jsonObject.getString("headimgurl"));
			userService.insert(user);
			return userId;
		}else return user.getId();
	}

}
