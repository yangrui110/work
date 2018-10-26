package com.lvshou.magic;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.lvshou.magic.money.export.ExportMoney;
import com.lvshou.magic.user.export.ExportUser;

@SpringBootApplication
@EnableScheduling
public class MagicAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicAdminApplication.class, args);
	}
	
	@Bean
	public ExportUser exportUser() {
		return new ExportUser();
	}

	@Bean
	public ExportMoney exportMoney() {
		return new ExportMoney();
	}
}
