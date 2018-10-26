package com.lvshou.magic.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CouponVo {

	private String id;
	private String title;
	private BigDecimal worth;
	private BigDecimal price;
	private BigDecimal vipPrice;
	private String describetion;
	private int defaults;
	private String createTime;
	private String startTime;
	private String arriveTime;
	private int num;
}
