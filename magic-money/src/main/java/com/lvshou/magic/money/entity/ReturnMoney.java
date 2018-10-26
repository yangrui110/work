package com.lvshou.magic.money.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ReturnMoney {
	private String id;
	private String userId;
	  private BigDecimal balance;
	  private BigDecimal integral;
	  private BigDecimal totalIntegral;
	  private BigDecimal reward;
	  private BigDecimal totalReward;
	  private String numId;
	  private String name;
	  private String phone;
	  private Date createTime;
	  private Date updateTime;
}
