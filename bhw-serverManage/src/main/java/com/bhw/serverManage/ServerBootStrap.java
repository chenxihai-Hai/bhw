package com.bhw.serverManage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class ServerBootStrap {
    public static void main(String[] args) {
        SpringApplication.run(ServerBootStrap.class, args);
    }
}
