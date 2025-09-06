package com.cms.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// src/main/java/com/cms/config/StaticResourceConfig.java
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
      .addResourceLocations("file:/C:/Users/Micheal/Desktop/ocr_image_save/");
  }
}
