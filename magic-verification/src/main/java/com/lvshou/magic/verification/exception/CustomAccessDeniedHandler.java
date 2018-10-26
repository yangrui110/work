package com.lvshou.magic.verification.exception;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        Map map = new HashMap();
        map.put("code", "402");
        map.put("message", "权限错误");
        map.put("data", accessDeniedException.getMessage());
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(new Date().getTime()));
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JSONObject.toJSONString(map));
	}

}
