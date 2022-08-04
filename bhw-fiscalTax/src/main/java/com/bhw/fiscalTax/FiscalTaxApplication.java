package com.bhw.fiscalTax;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bhw.fiscalTax.mapper")
public class FiscalTaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiscalTaxApplication.class, args);
	}

}
