package com.lvshou.magic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.formLogin()
		.loginPage("/ad_login")
		.and()
		.logout().logoutSuccessUrl("/ad_login")
		.and()
		.authorizeRequests()
		.antMatchers("/ad_login","/css/**","/js/**","/images/**","/img/**","/lib/**","/MP_verify_bQ9C8GkOLaxU7end.txt","/wechat/**","/**.jpg","/**.png").permitAll()
		.anyRequest().hasAuthority("admin")
		.and()
		.csrf().disable();
	}

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
	  return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

}
