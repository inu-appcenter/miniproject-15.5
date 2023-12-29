package com.example.mytodolist;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication
public class MyTodoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTodoListApplication.class, args);
    }

}
