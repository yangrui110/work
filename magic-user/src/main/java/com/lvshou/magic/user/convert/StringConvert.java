package com.lvshou.magic.user.convert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;
import com.lvshou.magic.user.status.RetailStatus;

public class StringConvert {

	private static final String sem="~";
	
	public static List<String> convertListUser(List<User> lists) throws Exception{
		List<String> results=new ArrayList<String>();
		for (User user : lists) {
			results.add(parseUser(user));
		}
		return results;
	}
	
	public static String getReferalCode(String str) {
		String[] results=str.split(";");
		for (String string : results) {
			if(string.contains("referralCode")) {
				string=string.replace("referralCode", "parentCode");
				return string;
			}
		}
		return null;
	}
	
	/**
	 * @param user 需要查找下级的用户
	 * @param lists 所有的用户集合，通常是由select * from user 查找得出并转化为List<String>
	 * @param results 遍历之后将结果存储在这个list链表之中
	 * @param classes 遍历的深度
	 * */
	public static void allChilds(String user,List<String> lists,List<String> results,int classes){
		if(classes<=0) return ;
		String code=getReferalCode(user);
		//这个链表主要是级别判断
		List<String> muni=new ArrayList<>();
		for (String strs : lists) {
			if(strs.contains(code)) {
				StringBuilder stringBuilder=new StringBuilder(strs);
				strs=stringBuilder.append("deep").append(sem).append(RetailStatus.classes-classes).toString();
				muni.add(strs);
				results.add(strs);
			}
		}
		int deep=classes-1;
		for (String string : muni) {
			allChilds(string, lists, results, deep);
		}
	}
	
	/**
	 * 转化符合要求的字符串成为User对象
	 * */
	public static Object toUserObject(String rs,Class basic) throws Exception {
		Object user=basic.newInstance();//将要返回的User对象，下面的所有都输解析rs参数，然后为user赋值
		Field[] fields=user.getClass().getDeclaredFields();
		String[] replace=rs.split(";");
		List<String> ls=new ArrayList<>();
		//获取到数据，并存放在List表中，方便后面的使用
		for (String string : replace) {
			String[] lm=string.split(sem);
			ls.add(lm[0]);
			if(lm[1].equals("null"))
				ls.add(null);
			else ls.add(lm[1]);
		}
		//开始遍历并且转化
		for (Field field : fields) {
			dealDiffrentClass(field,ls,user);
		}
		return user;
	}
	
	/**我们约定，第一个参数是Field类型,第二个参数是已经转化好的用户信息List，第三个参数是需要赋值的User对象*/
	private static void dealDiffrentClass(Object... args) throws Exception {
		Method method=null;
		Field field=(Field)args[0];
		Object user=args[2];
		List<String> ls=(List<String>) args[1];
		String type=field.getGenericType().toString();
		String name=field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
		
		if(type.equals("class java.lang.String")) {
			method=user.getClass().getDeclaredMethod("set"+name,String.class);
			String console=null;
			for(int i=0;i<ls.size();i=i+2) {
				if(ls.get(i).equalsIgnoreCase(name)) {
					if(ls.get(i+1)!=null) {
						console=ls.get(i+1);
					}
				}
			}
			method.invoke(user, console);
		}
		else if(type.equals("class java.util.Date")) {
			method=user.getClass().getDeclaredMethod("set"+name,Date.class);
			Date console=null;
			for(int i=0;i<ls.size();i=i+2) {
				if(ls.get(i).equalsIgnoreCase(name)){
					if(ls.get(i+1)!=null) {
						SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						console=dateFormat.parse(ls.get(i+1));
					}
				}
			}
			method.invoke(user, console);
		}
		else if(type.equals("class java.lang.Integer")) {
			method=user.getClass().getDeclaredMethod("set"+name,Integer.class);
			Integer console=null;
			for(int i=0;i<ls.size();i=i+2) {
				if(ls.get(i).equalsIgnoreCase(name)) {
					if(ls.get(i+1)!=null) {
						console=Integer.parseInt(ls.get(i+1));
					}
				}
			}
			method.invoke(user, console);
		}
	}
	
	/**
	 * 把User对象转化为符合要求的字符串形式
	 * */
	public static String parseUser(Object user) throws Exception {
		Field[] fields=user.getClass().getDeclaredFields();
		StringBuilder stringBuilder=new StringBuilder();
		for (Field field : fields) {
			field.setAccessible(true);
			Object object=field.get(user);
			String result="";
			if(object==null)
				result="null";
			else if (object instanceof java.sql.Date) {
				java.sql.Date date=(java.sql.Date)object;
				result =parseTime(date.getTime());
			}else if (object instanceof Timestamp) {
				Timestamp timestamp=(Timestamp)object;
				result =parseTime(timestamp.getTime());
			}
			else if(object instanceof Date) {
				Date date=(Date)object;
				result=parseTime(date.getTime());
			} else if (object instanceof String) {
				result=(String)object;
				if(StringUtils.isEmpty(result)) result="null";
			}else if (object instanceof Integer) {
				result=((Integer)object).toString();
			}else if (object instanceof BigDecimal) {
				result=((BigDecimal)object).toPlainString();
			}else
				result="null";
			stringBuilder.append(field.getName()).append(sem).append(result).append(";");
		}
		return stringBuilder.toString();
	}
	
	private static String parseTime(long time) {
		Date date2=new Date(time);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date2);
	}
}
