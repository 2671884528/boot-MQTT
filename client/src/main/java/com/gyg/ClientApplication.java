package com.gyg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author by gyg
 * @date 2021/10/10 12:29
 * @description
 */
@SpringBootApplication
@EnableSwagger2
@EnableIntegration
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
