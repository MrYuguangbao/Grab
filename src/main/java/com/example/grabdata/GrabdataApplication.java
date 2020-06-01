package com.example.grabdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@MapperScan(value = {"com.example.grabdata.mapper"})
@SpringBootApplication
@EnableScheduling
public class GrabdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrabdataApplication.class, args);
    }

}
