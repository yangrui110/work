package com.lvshou.magic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="lvshou.wxpay")
public class PayProperties {

	private String pay_notify;
	
	private String charge_notify;
	private String prefix_path;
}
