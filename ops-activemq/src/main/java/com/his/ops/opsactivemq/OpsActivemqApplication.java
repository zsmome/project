package com.his.ops.opsactivemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient //注册
@EnableFeignClients //负载
public class OpsActivemqApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpsActivemqApplication.class, args);
	}
}
