package org.wtm.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


//	//编程式
//	@Bean
//	RouteLocator routeLocator(RouteLocatorBuilder builder){
//
//		return builder.routes()
//				//配置规则  如果请求中带有get 将转发到http://httpbin.org
//				.route("javaboy_route",r->r.path("/get").uri("http://httpbin.org"))
//				.build();
//	}
}
