package com.lvshou.magic.order.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class OrderMain {

	@Id
	  private String id;
	  private String buyId;
	  private String buyName;
	  private String buyPhone;
	  private String buyProvince;
	  private String buyCity;
	  private String buyCountry;
	  private String buyAddress;
	  private int payStatus;
	  private int orderStatus;
	  private String createId;
	  private BigDecimal totalPrice;
	  private String userId;
	  private String describetion;
	  private Date createTime;
	  private Date updateTime;
	  
	  @OneToMany(targetEntity=OrderDetail.class,fetch=FetchType.LAZY)
	  @JoinColumn(name="mainId")
	  private List<OrderDetail> orderDetails=new LinkedList<>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getBuyProvince() {
		return buyProvince;
	}

	public void setBuyProvince(String buyProvince) {
		this.buyProvince = buyProvince;
	}

	public String getBuyCity() {
		return buyCity;
	}

	public void setBuyCity(String buyCity) {
		this.buyCity = buyCity;
	}

	public String getBuyCountry() {
		return buyCountry;
	}

	public void setBuyCountry(String buyCountry) {
		this.buyCountry = buyCountry;
	}

	public String getBuyName() {
		return buyName;
	}

	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}

	public String getBuyPhone() {
		return buyPhone;
	}

	public void setBuyPhone(String buyPhone) {
		this.buyPhone = buyPhone;
	}

	public String getBuyAddress() {
		return buyAddress;
	}

	public void setBuyAddress(String buyAddress) {
		this.buyAddress = buyAddress;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }


	  public String getBuyId() {
	    return buyId;
	  }

	  public void setBuyId(String buyId) {
	    this.buyId = buyId;
	  }


	  public int getPayStatus() {
	    return payStatus;
	  }

	  public void setPayStatus(int payStatus) {
	    this.payStatus = payStatus;
	  }


	  public int getOrderStatus() {
	    return orderStatus;
	  }

	  public void setOrderStatus(int orderStatus) {
	    this.orderStatus = orderStatus;
	  }


	  public String getDescribetion() {
	    return describetion;
	  }

	  public void setDescribetion(String describetion) {
	    this.describetion = describetion;
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
