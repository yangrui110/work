package com.lvshou.magic.user.vo;

import java.util.Date;

import lombok.Data;

@Data
public class UserVo {

	private String id;
	private String numId;
	  private String name;
	  private String phone;
	  private String identity;
	  private String mail;
	  private String province;
	  private String city;
	  private String country;
	  private String address;
	  private Date createTime;
	  private Date updateTime;
	  private String referralCode;
	  private Integer vip;
	  private String parentCode;
	  private int status;
	  private String access_token;
	  private Date vipTime;
	  private int preNum; //合伙人数量
	  private int direcNum;//董事数量
	  private String ownReferal;//自己设置的推荐码
}
