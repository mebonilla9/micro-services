package dev.manuel.notification;

import dev.manuel.amqp.RabbitMqMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
  scanBasePackages = {
    "dev.manuel.notification",
    "dev.manuel.amqp"
  }
)
@EnableEurekaClient
@EnableFeignClients(basePackages = "dev.manuel.clients")
public class NotificationApplication {
  public static void main(String[] args) {
    SpringApplication.run(NotificationApplication.class, args);
  }

  /*@Bean
  CommandLineRunner commandLineRunner(
    RabbitMqMessageProducer producer,
    NotificationConfig notificationConfig
  ) {
    return args -> {
      producer.publish(new Person("Manuel",33),
        notificationConfig.getInternalExchange(),
        notificationConfig.getInternalNotificationRoutingKey()
      );
    };
  }

  record Person (String name, int age){}*/
}
