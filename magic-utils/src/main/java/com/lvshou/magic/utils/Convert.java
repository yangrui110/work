package com.lvshou.magic.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Convert {

	public static String listToString(List list) {
		StringBuilder string=new StringBuilder();
		if(list==null) {
			return null;
		}
		for(int i=0;i<list.size();i++) {
			string=(i==list.size()-1)?string.append(list.get(i)):string.append(list.get(i)).append(",");
		}
		return string.toString();
	}
	public static List stringToList(String s) {
		List list=new ArrayList<Object>();
		if(s==null||s.equals("")) {
			return null;
		}
		return Arrays.asList(s.split(","));
	}
}
