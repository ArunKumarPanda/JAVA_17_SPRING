package com.adobe.prj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class OrderappApplication  {
	
	@Autowired
	CacheManager cacheManager;
	
	public static void main(String[] args) {
		SpringApplication.run(OrderappApplication.class, args);
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
