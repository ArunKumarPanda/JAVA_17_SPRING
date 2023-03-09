package com.adobe.prj;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.adobe.prj.entity.Product;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
//		cors.addMapping("/api/products")
//		
//		.allowedOrigins("http://domain2");
	
		ExposureConfiguration  exposureConfig  = config.getExposureConfiguration();
		exposureConfig.forDomainType(Product.class).withItemExposure((metadata, httpMethods) -> {
			return httpMethods.disable(HttpMethod.DELETE);
		});
		
	}
	
}
