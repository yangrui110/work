package com.lvshou.magic.operation.vo;

import java.math.BigDecimal;


public class OperationVo {

	 private String id;
	  private String operate;
	  private BigDecimal money;
	  private String describetion;
	  private Integer status;
	  private java.sql.Timestamp createTime;
	  private java.sql.Timestamp updateTime;
	 private String userId;
	 
	 private String name;
	 private String numId;
	 private String phone;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumId() {
		return numId;
	}
	public void setNumId(String numId) {
		this.numId = numId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	 
}
