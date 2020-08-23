package com.akash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.akash"})
@SpringBootApplication
public class SpringUtApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtApplication.class);

  public static void main(String[] args) {

    SpringApplication.run(SpringUtApplication.class, args);
  }
}
