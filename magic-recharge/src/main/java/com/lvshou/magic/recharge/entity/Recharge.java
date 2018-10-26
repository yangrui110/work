package com.lvshou.magic.recharge.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Recharge {

	@Id
	  private String id;
	  private String userId;
	  private String operator;
	  private BigDecimal money;
	  private String describetion;
	  private java.sql.Timestamp createTime;
	  private java.sql.Timestamp updateTime;


	  public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperator() {
	    return operator;
	  }

	  public void setOperator(String operator) {
	    this.operator = operator;
	  }


	  public BigDecimal getMoney() {
	    return money;
	  }

	  public void setMoney(BigDecimal money) {
	    this.money = money;
	  }


	  public String getDescribetion() {
	    return describetion;
	  }

	  public void setDescribetion(String describetion) {
	    this.describetion = describetion;
	  }


	  public java.sql.Timestamp getCreateTime() {
	    return createTime;
	  }

	  public void setCreateTime(java.sql.Timestamp createTime) {
	    this.createTime = createTime;
	  }


	  public java.sql.Timestamp getUpdateTime() {
	    return updateTime;
	  }

	  public void setUpdateTime(java.sql.Timestamp updateTime) {
	    this.updateTime = updateTime;
	  }

	}

