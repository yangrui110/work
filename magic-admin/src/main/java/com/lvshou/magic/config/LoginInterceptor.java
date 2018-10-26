package com.lvshou.magic.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		/*
		String uri=request.getRequestURI();
		boolean bl=SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		System.out.println("路径uri="+uri+"是否验证"+bl);
		if(uri.equals("/admin/user/userList")) {
			String name=SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println("name="+name);
			Cookie[] cookie=request.getCookies();
			for(int i=0;i<cookie.length;i++) {
				System.out.println("name="+cookie[i].getName()+"------value="+cookie[i].getValue());
			}
		}*/
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
