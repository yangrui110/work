package com.lvshou.magic.order.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;


@Entity
@DynamicUpdate
public class OrderDetail {

	@Id
	  private String id;
	  private String mainId;
	  private String mainCreateId;
	  private String productId;
	  private String productName;
	  private String productIcon;
	  private BigDecimal singlePrice;
	  private String evaluate;
	  private String pics;
	  private int num;
	  private BigDecimal price;
	  private String describetion;
	  private Date createTime;
	  private Date updateTime;


	  public String getMainCreateId() {
		return mainCreateId;
	}

	public void setMainCreateId(String mainCreateId) {
		this.mainCreateId = mainCreateId;
	}

	public String getProductIcon() {
		return productIcon;
	}

	public void setProductIcon(String productIcon) {
		this.productIcon = productIcon;
	}

	public BigDecimal getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(BigDecimal singlePrice) {
		this.singlePrice = singlePrice;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}


	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }



	  public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getProductId() {
	    return productId;
	  }

	  public void setProductId(String productId) {
	    this.productId = productId;
	  }


	  public int getNum() {
	    return num;
	  }

	  public void setNum(int num) {
	    this.num = num;
	  }


	  public BigDecimal getPrice() {
	    return price;
	  }

	  public void setPrice(BigDecimal price) {
	    this.price = price;
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
