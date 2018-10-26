package com.lvshou.magic.order.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lvshou.magic.order.entity.OrderDetail;

import lombok.Data;

@Data
public class OrderMainVo {
	  private String id;
	  private String buyId;
	  private String buyName;
	  private String buyPhone;
	  private String buyProvince;
	  private String buyCity;
	  private String buyCountry;
	  private String buyAddress;
	  private int payStatus;
	  private int orderStatus;
	  private String describetion;
	  private BigDecimal totalPrice;
	  private String createId;
	  private String userId;
	  private Date createTime;
	  private Date updateTime;
	  private List<OrderDetail> orderDetails;

}
