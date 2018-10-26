package com.lvshou.magic.user.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Message {

	@Id
	private String id;
	private String content;
	private String belongTo;
	private int status;
}
