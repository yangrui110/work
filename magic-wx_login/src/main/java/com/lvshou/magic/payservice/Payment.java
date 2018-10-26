package com.lvshou.magic.payservice;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

public interface Payment {

	public String sign(String notify,String openid,String mainId,String price,String ip) throws UnsupportedEncodingException;
	
	public boolean token(String request);
	
	public boolean reback(BigDecimal decimal);
	
}
