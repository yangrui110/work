package com.lvshou.magic.utils;

import java.math.BigDecimal;

public class MoneyUtil {
	
	public static BigDecimal calcMoney(int classes,int basic) {
		BigDecimal decimal=new BigDecimal(basic);
		for(int i=0;i<classes;i++) {
			decimal=decimal.divide(new BigDecimal(2));
		}
		return decimal;
	}
	
	public static BigDecimal leave(int classes,int basic) {
		BigDecimal decimal=new BigDecimal(basic);
		BigDecimal res=new BigDecimal(0);
		for (int i = 0; i < classes; i++) {
			res=res.add(calcMoney(i+1,basic));
		}
		res=decimal.subtract(res);
		return res;
	}
}
