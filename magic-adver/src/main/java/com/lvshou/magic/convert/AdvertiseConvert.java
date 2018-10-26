package com.lvshou.magic.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lvshou.magic.entity.Advertise;
import com.lvshou.magic.utils.Convert;
import com.lvshou.magic.vo.AdvertiseVo;

public class AdvertiseConvert {

	public static List<AdvertiseVo> convert(List<Advertise> list) {
		List<AdvertiseVo> list2=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			Advertise ad=list.get(i);
			AdvertiseVo vo=new AdvertiseVo();
			BeanUtils.copyProperties(ad, vo);
			vo.setPics(Convert.stringToList(ad.getPics()));
			list2.add(vo);
		}
		return list2;
	}
	public static Advertise convert(AdvertiseVo vo) {
		Advertise advertise=new Advertise();
		BeanUtils.copyProperties(vo, advertise);
		advertise.setPics(Convert.listToString(vo.getPics()));
		return advertise;
	}
	public static AdvertiseVo convert(Advertise advertise) {
		AdvertiseVo vo=new AdvertiseVo();
		BeanUtils.copyProperties(advertise, vo);
		vo.setPics(Convert.stringToList(advertise.getPics()));
		return vo;
	}
}
