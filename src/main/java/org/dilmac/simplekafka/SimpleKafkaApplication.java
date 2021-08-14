package org.dilmac.simplekafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SimpleKafkaApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleKafkaApplication.class, args);
  }
}
