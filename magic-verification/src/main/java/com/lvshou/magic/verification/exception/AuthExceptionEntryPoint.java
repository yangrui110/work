package com.lvshou.magic.verification.exception;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.alibaba.fastjson.JSONObject;

public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<>();
        map.put("code", "401");
        map.put("message", "access_token验证错误或者无access_token");
        map.put("data", authException.getMessage());
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            response.getWriter().write(JSONObject.toJSONString(map));
        } catch (Exception e) {
            throw new ServletException();
        }
	}

}
