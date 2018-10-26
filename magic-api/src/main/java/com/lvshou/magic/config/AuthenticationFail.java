package com.lvshou.magic.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;

public class AuthenticationFail{

/*	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		CommonException exception2=new CommonException(ResultEnum.LOGIN_ERROR);
        response.setStatus(exception2.getCode());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(exception2));
	}*/

}
