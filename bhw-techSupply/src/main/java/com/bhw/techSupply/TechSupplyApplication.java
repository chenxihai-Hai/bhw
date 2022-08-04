package com.bhw.techSupply;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.bhw.techSupply.mapper")
public class TechSupplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechSupplyApplication.class, args);
	}

}
