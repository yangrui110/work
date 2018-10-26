package com.lvshou.magic.account.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
public class BuyInfo {

	@Id
	  private String id;
	  private String name;
	  private String phone;
	  private String province;
	  private String city;
	  private String country;
	  private String address;
	  private String userId;
	  private int defaults;
	  private java.sql.Timestamp createTime;
	  private java.sql.Timestamp updateTime;


	  public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getDefaults() {
		return defaults;
	}

	public void setDefaults(int defaults) {
		this.defaults = defaults;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
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


	  public String getProvince() {
	    return province;
	  }

	  public void setProvince(String province) {
	    this.province = province;
	  }


	  public String getCity() {
	    return city;
	  }

	  public void setCity(String city) {
	    this.city = city;
	  }


	  public String getAddress() {
	    return address;
	  }

	  public void setAddress(String address) {
	    this.address = address;
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
