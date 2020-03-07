package org.wtm.consulconsumers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulConsumersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsulConsumersApplication.class, args);
	}



	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
