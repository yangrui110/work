package com.lvshou.magic.user.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class OldVip {

	@Id
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
	
}
