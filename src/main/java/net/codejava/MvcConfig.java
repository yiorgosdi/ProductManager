package net.codejava;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		//registry.addViewController("/login_success").setViewName("login_success");
		//registry.addViewController("/login_failure").setViewName("login_failure");
		//registry.addViewController("/logout_success").setViewName("logout_success");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		Path productUploadDir = Paths.get("./product-photos"); 
		String productPhotoPath = productUploadDir.toFile().getAbsolutePath(); 
				
		registry.addResourceHandler("/product-photos/**")
				.addResourceLocations("file:/" + productPhotoPath + "/"); 
	}
}
