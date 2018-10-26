package com.lvshou.magic.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="lvshou.security")
public class SecurityProperties {

	//内部配置登录时form表单提交的位置
	private String login_url;
	
	private String prefix_file_path;
	
	private String server_local_file_path;
	
}
