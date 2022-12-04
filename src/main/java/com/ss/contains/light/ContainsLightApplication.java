package com.ss.contains.light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@EnableCaching
@EnableR2dbcAuditing
@SpringBootApplication
public class ContainsLightApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainsLightApplication.class, args);
    }

}
