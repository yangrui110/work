package com.lvshou.magic.referral.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Referral {

	@Id
  private String id;
  private String userId;
  private String parentId;
  private Date createTime;
  private Date updateTime;
  private String childId;


  public String getId() {
    return id;
  }

  public Referral setId(String id) {
    this.id = id;
    return this;
  }


  public String getUserId() {
    return userId;
  }

  public Referral setUserId(String userId) {
    this.userId = userId;
    return this;
  }


  public String getParentId() {
    return parentId;
  }

  public Referral setParentId(String parentId) {
    this.parentId = parentId;
    return this;
  }


  public Date getCreateTime() {
    return createTime;
  }

  public Referral setCreateTime(Date createTime) {
    this.createTime = createTime;
    return this;
  }


  public Date getUpdateTime() {
    return updateTime;
  }

  public Referral setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
    return this;
  }


  public String getChildId() {
    return childId;
  }

  public Referral setChildId(String childId) {
    this.childId = childId;
    return this;
  }

}

