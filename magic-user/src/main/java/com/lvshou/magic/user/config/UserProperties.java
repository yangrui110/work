package com.lvshou.magic.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="lvshou.user")
public class UserProperties {

	private String code_path;
	
	private String prefix_local_path;
}
