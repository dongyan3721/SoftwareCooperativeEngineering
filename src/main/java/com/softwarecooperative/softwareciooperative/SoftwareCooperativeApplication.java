package com.softwarecooperative.softwareciooperative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SoftwareCooperativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareCooperativeApplication.class, args);
    }

}
