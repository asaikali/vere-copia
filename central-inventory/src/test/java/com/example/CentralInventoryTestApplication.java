package com.example;

import org.springframework.boot.SpringApplication;

public class CentralInventoryTestApplication {
        public static void main(String[] args) 
    {
        SpringApplication.from(CentralInventoryApplication::main).with(TestContainerConfiguration.class).run(args);
    }
}
