package com.lvshou.magic.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvshou.magic.user.entity.UserVo;

public interface WxLoginService {

	public String login();
	/**用户授权登录的回调方法，此方法会产生新的用户
	 * @throws Exception */
	public UserVo modify(HttpServletRequest request);
	public void loginSuccess(String openId,HttpServletResponse response) ;
	public String shangcheng();
	String getShareLocation(String code);
	String dealShare(HttpServletRequest request) throws Exception;
	/**判断用户的微信ID是否已经注册,这个方法并不会产生新的用户，如果要产生新的用户，请使用modify方法*/
	public UserVo register(HttpServletRequest request);
	String wxPerson();
}
