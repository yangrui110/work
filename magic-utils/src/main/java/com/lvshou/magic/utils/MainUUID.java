package com.lvshou.magic.utils;

import java.util.UUID;

public class MainUUID {

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	public static String getCode() {
		char[] arr= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<6;i++) {
			double a=Math.random();
			int b=(int) (a*25+1);
			builder.append(arr[b]);
		}
		return builder.toString();
	}
}
