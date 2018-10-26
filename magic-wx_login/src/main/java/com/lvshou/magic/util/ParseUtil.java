package com.lvshou.magic.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.lvshou.magic.statics.WeChatConfig;

public class ParseUtil {
	public static String parseFirst(SortedMap<String, String> map) {
			
			Set<String> set=map.keySet();
			StringBuilder builder=new StringBuilder();
			int i=1;
			System.out.println(set.size());
			for (String string : set) {
				builder.append(string);
				builder.append("=");
				builder.append(map.get(string));
				if(i!=set.size()) {
					builder.append("&");
					i++;
				}
			}
			builder.append("&key="+WeChatConfig.KEY);
			System.out.println(builder.toString());
			return builder.toString();
		}
	
}
