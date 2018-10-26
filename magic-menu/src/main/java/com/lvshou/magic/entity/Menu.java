package com.lvshou.magic.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@DynamicUpdate
public class Menu {

	@Id
	private String id;
	private String title;
	private String icon;
	private int type;
	private Date createTime;
	private Date updateTime;
}
