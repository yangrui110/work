package com.lvshou.magic.user.entity;
import java.util.Date;

import lombok.Data;

@Data
public class UserVo {
	private String id;
	  private String numId;
	  private String icon;
	  private String name;
	  private String phone;
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
	  private Date vipTime;
	  private String parentCode;
	  private String shareCode;
	  private Integer status;
	  private String directPush;
	  private String referName;
	  private String referArea;
	  private String wid;
	  private Integer deep;
	  private String ownReferCode;
	  private Integer ify; //判断是否已经绑定
	  private Integer rsname;//1.表示不可以修改，2表示可以修改
	  private Date prePartnerTime;
	  private Date referTime;
}
