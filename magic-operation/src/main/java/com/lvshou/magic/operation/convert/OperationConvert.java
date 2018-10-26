package com.lvshou.magic.operation.convert;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.operation.vo.OperationVo;

public class OperationConvert {

	/**lists是从数据库查到的数据，将会转换成OperationVo返回出去*/
	public static List<OperationVo> convertToVo(List lists) {
		List<OperationVo> operationVos=new ArrayList<OperationVo>();
		OperationVo vo=null;
		for (Object object : lists) {
			Object[] rs=(Object[]) object;
			vo=new OperationVo();
			vo.setId(parseString(rs[0]));
			vo.setOperate(parseString(rs[1]));
			vo.setMoney(parseBigDecimal(rs[2]));
			vo.setDescribetion(parseString(rs[3]));
			vo.setCreateTime(parseTime(rs[4]));
			vo.setUpdateTime(parseTime(rs[5]));
			vo.setStatus(parseInteger(rs[6]));
			vo.setUserId(parseString(rs[7]));
			vo.setNumId(parseString(rs[8]));
			vo.setName(parseString(rs[9]));
			vo.setPhone(parseString(rs[10]));
			operationVos.add(vo);
		}
		return operationVos;
	}
	
	private static String parseString(Object object) {
		if(object==null) return null;
		else return (String)object;
	}
	
	private static BigDecimal parseBigDecimal(Object object) {
		if(object==null) return new BigDecimal(0);
		else {
			if(object instanceof Integer) return new BigDecimal((Integer)object);
			else if(object instanceof BigDecimal) return (BigDecimal)object;
			else if(object instanceof String) return new BigDecimal((String)object);
			else return new BigDecimal(0);
		}
	}
	
	private static Timestamp parseTime(Object object) {
		if(object==null)return null;
		else {
			Timestamp stamp=(Timestamp) object;
			return stamp;
		}
	}
	private static Integer parseInteger(Object object) {
		if(object==null) return null;
		else {
			if(object instanceof Byte) {return ((Byte) object).intValue();}
			else return (Integer)object;
		}
	}
	
	public static Calendar parseCalendar(String date) throws ParseException {
		if(date==null) throw new CommonException(ResultEnum.ERROR);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
		Date date2=dateFormat.parse(date);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date2);
		return calendar;
	}
}
