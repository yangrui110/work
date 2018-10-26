package com.lvshou.magic.login;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.statics.WeChatConfig;
import com.lvshou.magic.user.convert.UserConvert;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.util.HttpUtils;
import com.lvshou.magic.utils.MainUUID;

import lombok.extern.slf4j.Slf4j;
import weixin.popular.api.SnsAPI;

@Service
@Slf4j
public class WxLoginServiceImpl implements WxLoginService{
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public String login() {
		// TODO Auto-generated method stub
		//String location=SnsAPI.connectOauth2Authorize(WeChatConfig.APPID, WeChatConfig.LOGIN_URL, true, "STATE");
		String location=String.format(WeChatConfig.LOGIN, WeChatConfig.APPID,WeChatConfig.LOGIN_URL,"snsapi_userinfo");
		//String location="https://www.baidu.com";
		System.out.println("localtion="+location);
		return location;
		/*try {
			response.sendRedirect(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	@Override
	public String wxPerson() {
		String location=String.format(WeChatConfig.LOGIN, WeChatConfig.APPID,WeChatConfig.PERSON,"snsapi_userinfo");
		return location;

	}
	@Override
	public String getShareLocation(String code) {
		String share_path=String.format(WeChatConfig.SHARE_PATH, code);
		String location=String.format(WeChatConfig.LOGIN, WeChatConfig.APPID,share_path,"snsapi_userinfo");
		return "redirect:"+location;
	}
	
	@Override
	public String dealShare(HttpServletRequest request) throws Exception {
		String code=request.getParameter("code");
		String parentCode=request.getParameter("parent");
		if(code==null) {throw new CommonException(ResultEnum.ERROR);}
		String url=String.format(WeChatConfig.USER_ACCESS_TOKEN, WeChatConfig.APPID,WeChatConfig.APPSECRET,code);
		/**返回的是个JSON字符串*/
		String result=HttpUtils.httpsRequest(url, "GET", null);
		JSONObject jsonObject=JSONObject.parseObject(result);
		String opendid=jsonObject.getString("openid");
		/**接下来获取用户的基本信息*/
		//1.先获取全局的access_token
		String token_url=String.format(WeChatConfig.ACCESS_TOKEN, WeChatConfig.APPID,WeChatConfig.APPSECRET);
		String tokeResult=HttpUtils.httpsRequest(token_url, "GET", null);
		JSONObject json_token=JSONObject.parseObject(tokeResult);
		String token=json_token.getString("access_token");
		//2.获取用户基本信息
		String info_url=String.format(WeChatConfig.USER_INFO, token,opendid);
		String info=HttpUtils.httpsRequest(info_url, "GET", null);
		JSONObject re=JSONObject.parseObject(info);
		//上面获取到的re就是用户的全部信息
		//接下来进行用户的判断
		//1.判断微信号为openid的用户是否存在系统中,不存在就插入一条记录，存在就直接返回
		User user=new User();
		user.setWid(opendid);
		User user2=userService.findUser(user);
		if(user2==null) {
			//1.进行插入操作
			//注册操作
			/**由于做了修改，这个地方并不能产生新的用户，将会跳转到update.html界面*/
			/*
			String id=MainUUID.getUUID();

			user.setId(id);
			user.setAddress("未填写");
			user.setName(re.getString("nickname"));
			user.setPhone("");
			user.setProvince(user.getProvince()==null?"未填写":user.getProvince());
			user.setCity(user.getCity()==null?"未填写":user.getCity());
			user.setCountry(user.getCountry()==null?"未填写":user.getCountry());
			user.setIcon(re.getString("headimgurl"));
			user.setDirectPush(parentCode);
			user.setParentCode(parentCode);
			user.setRsname(1);
			user.setIfy(2);
			userService.insert(user);*/
			//2.判断用户是否已经关注了微信公众号，没有关注，直接跳到关注界面
			int sub=re.getIntValue("subscribe");
			if(sub==0) return "http://www.wanxidi.com.cn/user/access.html";
			else return "http://www.wanxidi.com.cn/update.html?id="+opendid+"&parent="+parentCode;
		}else {
			//直接返回数据
			 return "http://www.wanxidi.com.cn/share/share.html?id="+user2.getId();
		}
	}
	
	@Override
	public String shangcheng() {
		String location=String.format(WeChatConfig.LOGIN, WeChatConfig.APPID,WeChatConfig.MEIYE_URL,"snsapi_userinfo");
		//String location="https://www.baidu.com";
		//System.out.println("localtion="+location);
		return location;
	}
	@Override
	public void loginSuccess(String openId,HttpServletResponse response) {
		
		User user =userService.findUser(new User().setId(openId));
		if(user==null) throw new CommonException(ResultEnum.ERROR);
		//把token放置到redis中
		//判断redis中是否已经存在了token数据
		String token=stringRedisTemplate.opsForValue().get("token_"+openId);
		if(StringUtils.isEmpty(token)) {
			token=MainUUID.getUUID();
		}
		stringRedisTemplate.opsForValue().set("token_"+openId, token, 7200,TimeUnit.SECONDS );
		//放置到cookie中
		Cookie cookie=new Cookie("token", token);
		cookie.setMaxAge(7200);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	@Override
	public UserVo modify(HttpServletRequest request){
		// TODO Auto-generated method stub
		weixin.popular.bean.user.User user=getUserInfo(request);
		String openId=user.getOpenid();
		//如果从数据库中查找到该记录，就直接登录，如果没有，需要进行注册操作
		User u=new User();
		u.setWid(openId);
		User user2=userService.findUser(u);
		if(user2==null) {
			//注册操作
			String id=MainUUID.getUUID();
			User user1=new User();
			user1.setId(id);
			user1.setWid(openId);
			user1.setAddress("未填写");
			user1.setName(user.getNickname());
			user1.setPhone("");
			user1.setProvince(user.getProvince()==null?"未填写":user.getProvince());
			user1.setCity(user.getCity()==null?"未填写":user.getCity());
			user1.setCountry(user.getCountry()==null?"未填写":user.getCountry());
			user1.setIfy(2);
			user1.setRsname(1);
			user1.setIcon(user.getHeadimgurl());
			try {
				userService.insert(user1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			UserVo vo=UserConvert.covertToVo(user1,0);
			return vo;
		}
		return UserConvert.covertToVo(user2,0);
	}

	@Override
	public UserVo register(HttpServletRequest request) {
		// TODO Auto-generated method stub
		weixin.popular.bean.user.User user=getUserInfo(request);
		String openId=user.getOpenid();
		User user2=new User();
		user2.setWid(openId);
		User user3=userService.findUser(user2);
		if(user3==null) {
			return UserConvert.covertToVo(user2, 0);
		}else return UserConvert.covertToVo(user3, 0);
	}
	
	private weixin.popular.bean.user.User getUserInfo(HttpServletRequest request){
		String code=request.getParameter("code");
		if(code==null) {throw new CommonException(ResultEnum.ERROR);}
		System.out.println(code);
		String token=SnsAPI.oauth2AccessToken(WeChatConfig.APPID,WeChatConfig.APPSECRET,code).getAccess_token();
		System.out.println("token="+token);
		weixin.popular.bean.user.User user=SnsAPI.userinfo(token, MainUUID.getUUID(), "zh_CN");
		return user;
	}
}
