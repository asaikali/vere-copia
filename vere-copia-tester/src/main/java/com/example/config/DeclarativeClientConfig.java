package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.example.exchange.VeraCopiaClient;

@Configuration
public class DeclarativeClientConfig 
{
	@Value("${vere-copia.service.identifier}")
	protected String veraCopiaServiceIdentifier;
	
	@Bean
	public VeraCopiaClient getVeraCopiaClient(WebClient.Builder builder, ExchangeFilterFunction filter)
	{
		final var client = builder.baseUrl(veraCopiaServiceIdentifier).filter(filter).build();
		final var factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();

		return factory.createClient(VeraCopiaClient.class);
	}
}
