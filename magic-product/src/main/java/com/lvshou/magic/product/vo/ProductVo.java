package com.lvshou.magic.product.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductVo {

	  private String id;
	  private String name;
	  private BigDecimal price;
	  private BigDecimal vipPrice;
	  private BigDecimal integral;
	  private Integer changeStatus;
	  private Integer stock;
	  private Integer status;
	  private String icon;
	  private List<String> pics;
	  private String evaluate;
	  private String detail;
	  private BigDecimal realPrice;
	  private java.sql.Timestamp createTime;
	  private java.sql.Timestamp updateTime;
}
