package com.homework.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.homework.task")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
