package com.lvshou.magic.user.convert;

import org.springframework.beans.BeanUtils;

import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.entity.UserVo;

public class UserConvert {

	public static UserVo covertToVo(User user,int deep) {
		UserVo vo=new UserVo();
		BeanUtils.copyProperties(user, vo);
		vo.setDeep(deep);
		return vo;
	}
	public static User covertToUser(UserVo vo) {
		User user=new User();
		BeanUtils.copyProperties(vo, user);
		return user;
	}
}
