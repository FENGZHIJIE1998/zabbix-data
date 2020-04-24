package com.dazhi.zabbixdata.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@PropertySource("classpath:/application.yml")
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 访问路径
     */
    @Value("${accessPath}")
    private String accessPath;
    @Value("${absolutePath}")
    private String absolutePath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(accessPath).addResourceLocations(absolutePath);
    }

}
