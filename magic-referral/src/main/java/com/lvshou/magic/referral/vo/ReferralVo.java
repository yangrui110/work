package com.lvshou.magic.referral.vo;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ReferralVo {

	private String userId;
	private String parentId;
	private String childId;
	private int classes;
	private Date createTime;
	private Date updateTime;
	
}
