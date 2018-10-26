package com.lvshou.magic.money.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class Reward {

	@Id
	private String id;
	private BigDecimal total;
	private String source;
	private String sourcePhone;
	private String sourceName;
	private String target;
	private String targetPhone;
	private String targetName;
	private Date createTime;
	private Date updateTime;
}
