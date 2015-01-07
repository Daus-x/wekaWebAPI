package org.weka.web.application.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.weka.web.application.controller.FilesController;
import org.weka.web.application.controller.SMOController;
import org.weka.web.application.controller.SimpleController;
import org.weka.web.application.controller.UploadController;

/**
 * Created by suad on 1/3/2015.
 */
@Configuration
@EnableWebMvc
public class WebAppConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public SimpleController simpleController(){
        return new SimpleController();
    }

    @Bean
    public MultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**").addResourceLocations("/public/");
    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public SMOController smoController(){return new SMOController();}

    @Bean
    public UploadController uploadController(){return new UploadController();}

    @Bean
    public FilesController filesController(){return new FilesController();}
}
