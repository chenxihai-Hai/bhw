package com.bhw.bhwtechology;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.bhw.bhwtechology.mapper")
public class BhwTechologyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BhwTechologyApplication.class, args);
	}

}
