package com.lvshou.magic.operation.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Operation {

  @Id
  private String id;
  private String operate;
  private BigDecimal money;
  private String describetion;
  private Integer status;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;
 private String userId;
 
public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOperate() {
    return operate;
  }

  public void setOperate(String operate) {
    this.operate = operate;
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
