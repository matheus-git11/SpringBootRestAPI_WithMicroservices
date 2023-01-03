package io.github.matheusgit11.msclientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroServiceClientesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceClientesApplication.class, args);
    }

}
