package dev.manuel.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
  scanBasePackages = {
    "dev.manuel.customer",
    "dev.manuel.amqp"
  }
)
@EnableEurekaClient
@EnableFeignClients(basePackages = "dev.manuel.clients")
public class CustomerApplication {
  public static void main(String[] args) {
    SpringApplication.run(CustomerApplication.class, args);
  }
}
