package com.lvshou.magic.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;

@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

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
		String token=request.getParameter("token");
		String userId=request.getParameter("userId");
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(userId)) {
			throw new CommonException(ResultEnum.LOGIN);
		}
		
		//判断与redis内部的token值是否一致
		String redisToken=stringRedisTemplate.opsForValue().get("token_"+userId);
		if(!token.equals(redisToken)) {
			throw new CommonException(ResultEnum.LOGIN);
		}
		return true;
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
