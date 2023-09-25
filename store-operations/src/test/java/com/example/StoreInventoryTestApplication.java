package com.example;

import org.springframework.boot.SpringApplication;

public class StoreInventoryTestApplication {
	
    public static void main(String[] args) 
    {
        SpringApplication.from(StoreInventoryApplication::main).with(TestContainerConfiguration.class).run(args);
    }
}
