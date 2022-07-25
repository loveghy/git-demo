package com.qn.computersell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.Mapping;

@SpringBootApplication
@MapperScan("com.qn.computersell.mapper")
public class ComputersellApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComputersellApplication.class, args);
    }
}
