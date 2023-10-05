package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.inventory.WebInterceptor;

@SpringBootApplication
public class StoreInventoryApplication implements WebMvcConfigurer
{
  @Autowired
  protected WebInterceptor interceptor;

  public static void main(String[] args) {
    SpringApplication.run(StoreInventoryApplication.class, args);
  }

	@Override
	public void addInterceptors(InterceptorRegistry registry) 
  {
		registry.addInterceptor(interceptor);
	}  
}
