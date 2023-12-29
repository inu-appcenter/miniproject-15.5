package com.example.actions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ActionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActionsApplication.class, args);
    }

}
