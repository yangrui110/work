package com.lvshou.magic.api;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvshou.magic.user.convert.UserConvert;
import com.lvshou.magic.user.dao.UserDao;
import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.service.UserService;
import com.lvshou.magic.user.status.VipStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OldVipMoney {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void test() throws Exception {
		List<User> users=userDao.findAll();
		for (User user : users) {
			user.setVip(VipStatus.VIP);
			userService.update(UserConvert.covertToVo(user, 0));
		}
	}
	
}
