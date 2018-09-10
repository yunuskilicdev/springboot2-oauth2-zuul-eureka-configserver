package com.kilic.yunus.advertiseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdvertiseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvertiseServiceApplication.class, args);
    }
}
