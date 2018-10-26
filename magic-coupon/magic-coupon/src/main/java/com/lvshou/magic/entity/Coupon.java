package com.lvshou.magic.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@DynamicUpdate
@Data
@Entity
public class Coupon {

	@Id
	private String id;
	private String title;
	private BigDecimal worth;
	private BigDecimal price;
	private BigDecimal vipPrice;
	private String describetion;
	private Date createTime;
	private Date startTime;
	private Date arriveTime;
	private int defaults;
	private int num;
}
