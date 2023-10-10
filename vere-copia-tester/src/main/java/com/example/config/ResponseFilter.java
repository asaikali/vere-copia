package com.example.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseFilter 
{
    @Autowired
    protected MeterRegistry registry;

    @Bean
    public ExchangeFilterFunction inventoryResponseFilter()
    {
        return ExchangeFilterFunction.ofResponseProcessor(response -> 
        {
            var header = response.headers().header("request-target");
            if (header != null && header.size() > 0)
            {
                registry.counter("vc-client.http.request", "target", header.get(0)).increment();
            }

            return Mono.just(response);
        });
    }    
}
