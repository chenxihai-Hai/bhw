package com.bhw.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class ConfigBootStrap {
    public static void main(String[] args){
        SpringApplication.run(ConfigBootStrap.class,args);
    }
}
