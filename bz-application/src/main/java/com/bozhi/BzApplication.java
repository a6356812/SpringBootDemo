package com.bozhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bozhi.mapper")
public class BzApplication {

    public static void main(String[] args) {
        SpringApplication.run(BzApplication.class, args);
    }

}
