package io.github.matheusgit11.mscartoes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@Slf4j
public class MicroServiceCartoesApplication {

    public static void main(String[] args) {
        log.info("informacao: {}", "teste info");
        log.error("Error: {}", "teste erro");
        log.warn("Aviso: {}", "teste warn");
        SpringApplication.run(MicroServiceCartoesApplication.class, args);
    }

}
