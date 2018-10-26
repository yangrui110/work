package com.lvshou.magic.user.entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.lvshou.magic.entity.Coupon;


@Entity
public class User {

  @Id
  private String id;
  private String numId;
  private String icon;
  private String name;
  private String phone;
 // private Date birthday;
  private String identity;
  private String bankName;
  private String bankCard;
  private String mail;
  private String province;
  private String city;
  private String country;
  private String address;
  private Date createTime;
  private Date updateTime;
  private String referralCode;
  private String qrcode;
  private Integer vip;
  private String parentCode;
  private String shareCode;
  private Date vipTime;
  private Integer status;
  private String directPush;
  private String referName;
  private String referArea;
  private String wid;
  private Integer ify; //判断是否已经绑定
  private Integer rsname;//1.表示不可以修改，2表示可以修改
  private Date prePartnerTime;
  private Date referTime; //最后一次推荐新用户的时间
  
public Date getReferTime() {
	return referTime;
}

public void setReferTime(Date referTime) {
	this.referTime = referTime;
}

public Date getPrePartnerTime() {
	return prePartnerTime;
}

public void setPrePartnerTime(Date prePartnerTime) {
	this.prePartnerTime = prePartnerTime;
}

public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

public Integer getIfy() {
	return ify;
}

public void setIfy(Integer ify) {
	this.ify = ify;
}

public Integer getRsname() {
	return rsname;
}

public void setRsname(Integer rsname) {
	this.rsname = rsname;
}

public Date getVipTime() {
	return vipTime;
}

public void setVipTime(Date vipTime) {
	this.vipTime = vipTime;
}

public String getBankName() {
	return bankName;
}

public void setBankName(String bankName) {
	this.bankName = bankName;
}

public String getBankCard() {
	return bankCard;
}

public void setBankCard(String bankCard) {
	this.bankCard = bankCard;
}

public String getDirectPush() {
	return directPush;
}

public void setDirectPush(String directPush) {
	this.directPush = directPush;
}

public String getWid() {
	return wid;
}

public void setWid(String wid) {
	this.wid = wid;
}

public String getReferName() {
	return referName;
}

public void setReferName(String referName) {
	this.referName = referName;
}

public String getReferArea() {
	return referArea;
}

public void setReferArea(String referArea) {
	this.referArea = referArea;
}

@ManyToMany(targetEntity=Coupon.class)
  @JoinTable(name="user_coupon",
  joinColumns=
      @JoinColumn(name="userId", referencedColumnName="id"),
  inverseJoinColumns=
      @JoinColumn(name="couponId", referencedColumnName="id")
  )
  private Set<Coupon> coupons=new HashSet<>();
  

  public String getIcon() {
	return icon;
}

public String getShareCode() {
	return shareCode;
}

public User setShareCode(String shareCode) {
	this.shareCode = shareCode;
	return this;
}

public void setIcon(String icon) {
	this.icon = icon;
}

public String getNumId() {
	return numId;
}

public String getQrcode() {
	return qrcode;
}

public void setQrcode(String qrcode) {
	this.qrcode = qrcode;
}

public User setNumId(String numId) {
	this.numId = numId;
	return this;
}

public Set<Coupon> getCoupons() {
	return coupons;
}

public void setCoupons(Set<Coupon> coupons) {
	this.coupons = coupons;
}



public String getParentCode() {
	return parentCode;
}

public User setParentCode(String parentCode) {
	this.parentCode = parentCode;
	return this;
}

public String getCountry() {
	return country;
}

public User setCountry(String country) {
	this.country = country;
	return this;
}

public Integer getVip() {
	return vip;
}

public User setVip(Integer vip) {
	this.vip = vip;
	return this;
}

public String getId() {
    return id;
  }

  public User setId(String id) {
    this.id = id;
    return this;
  }


  public String getName() {
    return name;
  }

  public User setName(String name) {
    this.name = name;
    return this;
  }


  public String getPhone() {
    return phone;
  }

  public User setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public String getIdentity() {
    return identity;
  }

  public User setIdentity(String identity) {
    this.identity = identity;
    return this;
  }


  public String getMail() {
    return mail;
  }

  public User setMail(String mail) {
    this.mail = mail;
    return this;
  }


  public String getProvince() {
    return province;
  }

  public User setProvince(String province) {
    this.province = province;
    return this;
  }


  public String getCity() {
    return city;
  }

  public User setCity(String city) {
    this.city = city;
    return this;
  }


  public String getAddress() {
    return address;
  }

  public User setAddress(String address) {
    this.address = address;
    return this;
  }


  public Date getCreateTime() {
    return createTime;
  }

  public User setCreateTime(Date createTime) {
    this.createTime = createTime;
    return this;
  }


  public Date getUpdateTime() {
    return updateTime;
  }

  public User setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  public String getReferralCode() {
    return referralCode;
  }

  public User setReferralCode(String referralCode) {
    this.referralCode = referralCode;
    return this;
  }
}
