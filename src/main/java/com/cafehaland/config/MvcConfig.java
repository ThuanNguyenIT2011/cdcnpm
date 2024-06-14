package com.cafehaland.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "user-photo";
		Path pathPhotoDir = Paths.get(dirName);
		String userPhotoPath = pathPhotoDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/" + dirName + "/**")
				.addResourceLocations("file:/" + userPhotoPath + "/");
		
		
		String dirNameProduct = "product-photo";
		Path pathPhotoDirProduct = Paths.get(dirNameProduct);
		String userPhotoPathProduct = pathPhotoDirProduct.toFile().getAbsolutePath();
		registry.addResourceHandler("/" + dirNameProduct + "/**")
				.addResourceLocations("file:/" + userPhotoPathProduct + "/");
	}
	
}
