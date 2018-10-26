package com.lvshou.magic.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.lvshou.magic.config.properties.SecurityProperties;
import com.lvshou.magic.user.config.UserProperties;

@Configuration
@EnableConfigurationProperties({SecurityProperties.class,UserProperties.class,PayProperties.class})
public class EnableSecurityProperties {

}
