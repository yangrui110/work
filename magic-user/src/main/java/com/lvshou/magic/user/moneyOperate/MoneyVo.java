package com.lvshou.magic.user.moneyOperate;

import java.math.BigDecimal;
import java.util.Date;

import com.lvshou.magic.user.entity.User;

import lombok.Data;

@Data
public class MoneyVo {
	  private String id;
	  private String userId;
	  private String account;
	  private BigDecimal balance;
	  private BigDecimal integral;
	  private BigDecimal totalIntegral;
	  private BigDecimal reward;
	  private BigDecimal totalReward;
	  private Date createTime;
	  private Date updateTime;
	  private BigDecimal monthReward;
	  private User user;
}
