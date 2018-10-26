package com.lvshou.magic.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.lvshou.magic.user.entity.User;
import com.lvshou.magic.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	@Autowired
	UserService userService;
	
	@Test
	public void test() {
		System.out.println("ok");
		Page<User> page=userService.findByPhone("15672498519", 0, 10);
		System.out.println(page.getContent().size());
		
	}
}
