package com.lvshou.magic.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@DynamicUpdate
public class Advertise {

	@Id
	private String id;
	private int sort;
	private int type;
	private String pics;
	private String describetion;
	private Date createTime;
	private Date updateTime;
}
