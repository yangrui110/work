package com.lvshou.magic.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="user_coupon")
public class UserCoupon {

	@Id
	private String id;
	private String userId;
	private String couponId;
}
