package com.lvshou.magic.order.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lvshou.magic.order.entity.OrderMain;
import com.lvshou.magic.order.vo.OrderMainVo;

public class OrderMainConvert {

	public static OrderMainVo convertToVo(OrderMain orderMain) {
		if(orderMain==null) {
			return null;
		}
		OrderMainVo vo=new OrderMainVo();

		vo.setBuyId(orderMain.getBuyId());
		vo.setId(orderMain.getId());
		vo.setBuyName(orderMain.getBuyName());
		vo.setBuyPhone(orderMain.getBuyPhone());
		vo.setBuyProvince(orderMain.getBuyProvince());
		vo.setBuyCity(orderMain.getBuyCity());
		vo.setBuyAddress(orderMain.getBuyAddress());
		vo.setBuyCountry(orderMain.getBuyCountry());
		vo.setOrderStatus(orderMain.getOrderStatus());
		vo.setPayStatus(orderMain.getPayStatus());
		vo.setCreateTime(orderMain.getCreateTime());
		vo.setUpdateTime(orderMain.getUpdateTime());
		vo.setTotalPrice(orderMain.getTotalPrice());
		vo.setDescribetion(orderMain.getDescribetion());
		vo.setCreateId(orderMain.getCreateId());
		vo.setUserId(orderMain.getUserId());
		return vo;
	} 
	public static OrderMain convertToMain(OrderMainVo vo) {
		if(vo==null){
			return null;
		}
		OrderMain main=new OrderMain();
		BeanUtils.copyProperties(vo, main);
		return main;
	}
	
	public static List<OrderMainVo> convertList(List<OrderMain> list){
		List<OrderMainVo> vos=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			OrderMainVo vo=convertToVo(list.get(i));
			
			vos.add(i, vo);
		}
		return vos;
	}
}
