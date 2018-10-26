package com.lvshou.magic.user.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class VipHistory {

	@Id
	private String id;
	private int vip;
	private Date createTime;
	private Date updateTime;
	
	@OneToOne
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
}
