package com.adobe.prj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@EnableScheduling
@EnableHypermediaSupport(type = HypermediaType.HAL_FORMS)
@SpringBootApplication
public class OrderappApplication  {
	
	@Autowired
	CacheManager cacheManager;
	
	public static void main(String[] args) {
		SpringApplication.run(OrderappApplication.class, args);
	}
	
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
//https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
//	@Scheduled(fixedRate = 1000)
//	@Scheduled(fixedDelay = 1000)
	@Scheduled(cron = "0 0/30 * * * *")
//	@Scheduled(cron = "*/10 * * * * *")
	public void clearCache() {
		System.out.println("clear cache!!!");
		cacheManager.getCacheNames().forEach(name -> {
			cacheManager.getCache(name).clear();
		});
	}

}
