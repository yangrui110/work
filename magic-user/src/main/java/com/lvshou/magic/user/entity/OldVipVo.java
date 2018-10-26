package com.lvshou.magic.user.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class OldVipVo {
	private String id;
	private String numId;
	private String name;
	private String phone;
	private Date payTime;
	private String clerk;
	private String payMethod;
	private String bankName;
	private String bankCard;
	private String position;
	private String referName;
	private String referArea;
	
	private BigDecimal totalReward;
	private int deep;
}
