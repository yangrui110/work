package com.lvshou.magic.account.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Account {

	  @Id
	  private String id;
	  private String userId;
	  private String account;
	  private String type;
	  private java.sql.Timestamp createTime;
	  private java.sql.Timestamp updateTime;
	
	
	  public String getId() {
	    return id;
	  }
	
	  public Account setId(String id) {
	    this.id = id;
	    return this;
	  }
	
	
	  public String getUserId() {
	    return userId;
	  }
	
	  public Account setUserId(String userId) {
	    this.userId = userId;
	    return this;
	  }
	
	
	  public String getAccount() {
	    return account;
	  }
	
	  public Account setAccount(String account) {
	    this.account = account;
	    return this;
	  }
	
	
	  public String getType() {
	    return type;
	  }
	
	  public Account setType(String type) {
	    this.type = type;
	    return this;
	  }
	
	
	  public java.sql.Timestamp getCreateTime() {
	    return createTime;
	  }
	
	  public Account setCreateTime(java.sql.Timestamp createTime) {
	    this.createTime = createTime;
	    return this;
	  }
	
	
	  public java.sql.Timestamp getUpdateTime() {
	    return updateTime;
	  }
	
	  public Account setUpdateTime(java.sql.Timestamp updateTime) {
	    this.updateTime = updateTime;
	    return this;
	  }

}