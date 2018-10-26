package com.lvshou.magic.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lvshou.magic.order.entity.OrderDetail;
import com.lvshou.magic.order.service.OrderDetailService;
import com.lvshou.magic.order.service.OrderMainService;
import com.lvshou.magic.order.vo.OrderMainVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMainTest {

	@Autowired
	OrderMainService orderMainService;
	
	@Autowired
	OrderDetailService orderDetailService;
	@Test
	public void test() {
		OrderMainVo vo=new OrderMainVo();
		vo.setBuyId("2e9f8c2f69244d7eb5c76aa5a7ad7d47");
		vo.setDescribetion("mixixi");
		vo.setOrderStatus(1);
		vo.setPayStatus(1);
		vo.setTotalPrice(new BigDecimal(0));
		List<OrderDetail> list=new ArrayList<>();
		OrderDetail detail=new OrderDetail();
		detail.setProductId("2fe1326741cf40039fa860568eda0f22");
		detail.setNum(10);
		detail.setDescribetion("香蕉太好吃了");
		list.add(detail);
		vo.setOrderDetails(list);
		orderMainService.createOrder(vo);
	}
}
