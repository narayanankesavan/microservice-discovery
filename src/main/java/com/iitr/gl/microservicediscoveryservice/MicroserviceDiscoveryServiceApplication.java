package com.iitr.gl.microservicediscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MicroserviceDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceDiscoveryServiceApplication.class, args);
    }

}
