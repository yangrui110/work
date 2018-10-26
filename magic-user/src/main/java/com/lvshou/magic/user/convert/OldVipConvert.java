package com.lvshou.magic.user.convert;

import org.springframework.beans.BeanUtils;

import com.lvshou.magic.user.entity.OldVip;
import com.lvshou.magic.user.entity.OldVipVo;

public class OldVipConvert {

	public static OldVipVo convertToVo(OldVip oldvip,int deep) {
		OldVipVo vo=new OldVipVo();
		BeanUtils.copyProperties(oldvip, vo);
		vo.setDeep(deep);
		return vo;
	}
}
