package com.lvshou.magic.money.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Money {

	@Id
  private String id;
  private String userId;
  private BigDecimal balance;
  private BigDecimal integral;
  private BigDecimal totalIntegral;
  private BigDecimal reward;
  private BigDecimal totalReward;
  private Timestamp createTime;
  private Timestamp updateTime;
  @Transient
  private String numId;
  @Transient
  private String name;
  @Transient
  private String phone;
  @Transient
  private BigDecimal monthReward;
  
  
  public BigDecimal getMonthReward() {
	return monthReward;
}

public void setMonthReward(BigDecimal monthReward) {
	this.monthReward = monthReward;
}

public String getNumId() {
	return numId;
}

public void setNumId(String numId) {
	this.numId = numId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getId() {
    return id;
  }

  public Money setId(String id) {
    this.id = id;
    return this;
  }


  public String getUserId() {
    return userId;
  }

  public Money setUserId(String userId) {
    this.userId = userId;
    return this;
  }


  public BigDecimal getBalance() {
    return balance;
  }

  public Money setBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }


  public BigDecimal getIntegral() {
    return integral;
  }

  public Money setIntegral(BigDecimal integral) {
    this.integral = integral;
    return this;
  }


  public BigDecimal getTotalIntegral() {
    return totalIntegral;
  }

  public Money setTotalIntegral(BigDecimal totalIntegral) {
    this.totalIntegral = totalIntegral;
    return this;
  }


  public BigDecimal getReward() {
    return reward;
  }

  public Money setReward(BigDecimal reward) {
    this.reward = reward;
    return this;
  }


  public BigDecimal getTotalReward() {
    return totalReward;
  }

  public Money setTotalReward(BigDecimal totalReward) {
    this.totalReward = totalReward;
    return this;
  }


  public Timestamp getCreateTime() {
    return createTime;
  }

  public Money setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
    return this;
  }


  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public Money setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
    return this;
  }
}
