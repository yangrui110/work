package com.lvshou.magic.product.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Product {

	@Id
  private String id;
  private String name;
  private BigDecimal price;
  private String icon;
  private String pics;
  private BigDecimal vipPrice;
  private BigDecimal integral;
  private Integer changeStatus;//是否可以积分兑换
  private Integer stock;//产品库存量
  private Integer status;//产品销售状态
  private String evaluate;//产品评价
  private String detail;
  private Date createTime;
  private Date updateTime;


public Integer getChangeStatus() {
	return changeStatus;
}

public void setChangeStatus(Integer changeStatus) {
	this.changeStatus = changeStatus;
}

public String getDetail() {
	return detail;
}

public void setDetail(String detail) {
	this.detail = detail;
}

public BigDecimal getIntegral() {
	return integral;
}

public void setIntegral(BigDecimal integral) {
	this.integral = integral;
}

public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

public BigDecimal getVipPrice() {
	return vipPrice;
}

public void setVipPrice(BigDecimal vipPrice) {
	this.vipPrice = vipPrice;
}

public Integer getStock() {
	return stock;
}

public void setStock(Integer stock) {
	this.stock = stock;
}

public String getId() {
    return id;
  }

  public Product setId(String id) {
    this.id = id;
    return this;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }


  public String getPics() {
    return pics;
  }

  public void setPics(String pics) {
    this.pics = pics;
  }


  public String getEvaluate() {
    return evaluate;
  }

  public void setEvaluate(String evaluate) {
    this.evaluate = evaluate;
  }


  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

}
