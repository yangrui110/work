package com.lvshou.magic.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AdvertiseVo {
	private String id;
	private List<String> pics;
	private String describetion;
	private Date createTime;
	private Date updateTime;
}
