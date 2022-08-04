package com.bhw.legalServer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.bhw.legalServer.mapper")
public class LegalServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegalServerApplication.class, args);
	}

}
