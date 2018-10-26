package com.lvshou.magic.message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;

public interface MessageService {

	public String response(HttpServletRequest request,String adverPath) throws Exception;
}
