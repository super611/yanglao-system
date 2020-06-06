package com.yanglao.paymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PayManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayManagementApplication.class, args);
    }

}
