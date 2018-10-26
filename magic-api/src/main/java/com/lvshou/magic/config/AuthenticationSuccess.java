package com.lvshou.magic.config;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationSuccess {
/*
	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		System.out.println("登录成功！！！喵");
		String clientId=null;
		String secret=null;
		
		String header = request.getHeader("Authorization");
		if(header == null || !header.startsWith("Basic ")) {
			//第三方登录时
			clientId=request.getHeader("client");
			secret=request.getHeader("secret");
			if(clientId==null) {
				throw new UnapprovedClientAuthenticationException("请求头中无clientId信息");
			}
		}else {
			String[] tokens = extractAndDecodeHeader(header, request);
			assert tokens.length == 2;

			clientId = tokens[0];
			secret=tokens[1];
		}

		
		
		ClientDetails clientDetails=clientDetailsService.loadClientByClientId(clientId);
		if(clientDetails==null) {
			throw new UnapprovedClientAuthenticationException("没有clientId的配置信息,clientId="+clientId);
		}else if (!secret.equals(clientDetails.getClientSecret())) {
			throw new UnapprovedClientAuthenticationException("clientSecret密码不匹配");
		}
		TokenRequest tokenRequest=new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "custom");
		
		OAuth2Request oAuth2Request=tokenRequest.createOAuth2Request(clientDetails);
		
		OAuth2Authentication auth2Authentication=new OAuth2Authentication(oAuth2Request, authentication);
		
		OAuth2AccessToken oAuth2AccessToken=authorizationServerTokenServices.createAccessToken(auth2Authentication);
		
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(oAuth2AccessToken));
	}
	
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded, "utf-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}
*/
}
