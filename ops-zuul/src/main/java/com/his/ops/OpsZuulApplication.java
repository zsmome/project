package com.his.ops;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringCloudApplication
@EnableZuulProxy
@EnableFeignClients //负载
@EnableEurekaClient
public class OpsZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpsZuulApplication.class, args);
	}
}