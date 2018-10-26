package com.lvshou.magic.menu;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lvshou.magic.statics.WeChatConfig;
import com.lvshou.magic.util.HttpUtils;

@Service
public class MenuCreateImpl implements MenuCreateInterface {

	@Override
	public String create(){
		// TODO Auto-generated method stub
		String result=HttpUtils.httpsRequest(WeChatConfig.ACCESS_TOKEN, "GET", null);
		JSONObject jsonObject=new JSONObject(result);
		Object object=jsonObject.get("access_token");
		if(object==null) {
			return null;
		}
		System.out.println("result="+result);
		LoggerFactory.getLogger(MenuCreateImpl.class).info("access_token"+(String)object);
		String url=String.format(WeChatConfig.CREATE_MENU, (String)object);
		JSONArray array=new JSONArray();
		JSONObject j1=new JSONObject();
		j1.put("type", "view");
		j1.put("name", "美业商城");
		j1.put("key", "V1001_SHOP");
		j1.put("url", "http://api.wanxidi.com.cn/wechat/login");
		//j1.put("url", "http://api.wanxidi.com.cn/wechat/meiyeLogin");
		array.put(j1);
		JSONObject j2=new JSONObject();
		/*j2.put("type", "click");
		j2.put("name", "分享海报");
		j2.put("key", "V1001_SHARE");*/
		//j2.put("url", "http://www.wanxidi.com.cn/share/share.html");
		j2.put("type", "view");
		j2.put("name", "分享海报");
		j2.put("key", "V1001_STOP");
		j2.put("url", "http://www.wanxidi.com.cn/share/basic.html");
		array.put(j2);
		JSONObject j3=new JSONObject();
		j3.put("type", "view");
		j3.put("name", "个人中心");
		j3.put("key", "V1001_PERSON");
		j3.put("url", "http://api.wanxidi.com.cn/wechat/login");
		//j3.put("url", "http://www.sanguohf.top/wechat/login");
		array.put(j3);
		JSONObject jObject=new JSONObject();
		jObject.put("button",array);
		result=HttpUtils.httpsRequest(url, "POST",jObject.toString());
		return result;
	}

}
