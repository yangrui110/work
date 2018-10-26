package com.lvshou.magic.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice(basePackages="com.lvshou.magic")
public class JsonAdvice extends AbstractJsonpResponseBodyAdvice {
	
	public JsonAdvice() {
		super("callback","jsonp");
	}

}
