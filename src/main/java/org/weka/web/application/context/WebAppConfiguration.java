package org.weka.web.application.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.weka.web.application.controller.SimpleController;

/**
 * Created by suad on 1/3/2015.
 */
@Configuration
@EnableWebMvc
public class WebAppConfiguration {

    @Bean
    public SimpleController simpleController(){
        return new SimpleController();
    }

}
